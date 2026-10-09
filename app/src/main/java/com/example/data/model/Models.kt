package com.example.data.model

import androidx.annotation.DrawableRes
import com.example.R

enum class OrderStatus(val displayName: String, val stepIndex: Int) {
    CONFIRMED("Confirmed", 0),
    COOKING("Cooking", 1),
    OUT_FOR_DELIVERY("Out for Delivery", 2),
    DELIVERED("Delivered", 3),
    CANCELLED("Cancelled", -1);

    fun canBeCancelled(): Boolean {
        return this == CONFIRMED || this == COOKING || this == OUT_FOR_DELIVERY
    }
}

data class UserProfile(
    val id: String = "USR_101",
    val username: String = "mohammad_galib",
    val fullName: String = "Mohammad Galib",
    val mobile: String = "9820098701",
    val email: String = "galib@example.com",
    val address: String = "Pipe Line Road, Tandalja",
    val city: String = "Vadodara",
    val state: String = "Gujarat",
    val pincode: String = "390001"
)

data class FoodItem(
    val id: Long,
    val name: String,
    val category: String,
    val isVeg: Boolean,
    val price: Double,
    @DrawableRes val imageResId: Int = com.example.R.drawable.dish_biryani,
    val imageUrls: List<String> = emptyList(),
    val vendorName: String = "Food World Kitchen",
    val vendorCity: String = "Vadodara",
    val vendorState: String = "Gujarat",
    val available: Boolean = true,
    val description: String,
    val rating: Float = 4.5f,
    val prepTimeMinutes: Int = 25
) {
    val displayImages: List<Any>
        get() = if (imageUrls.isNotEmpty()) imageUrls else listOf(imageResId)

    val mainImage: Any
        get() = imageUrls.firstOrNull() ?: imageResId
}

data class CartItem(
    val foodItem: FoodItem,
    val quantity: Int
) {
    val totalPrice: Double get() = foodItem.price * quantity
}

data class Order(
    val id: String,
    val orderTime: String,
    val items: List<CartItem>,
    val customerName: String = "Mohammad Galib",
    val customerMobile: String = "9820098701",
    val deliveryAddress: String,
    val deliveryCity: String,
    val deliveryState: String,
    val deliveryPincode: String = "390001",
    val paymentMethod: String = "Cash on Delivery",
    val paymentStatus: String = "Pending",
    val orderStatus: OrderStatus = OrderStatus.CONFIRMED,
    val subtotal: Double,
    val deliveryFee: Double = 30.0,
    val tax: Double = 15.0,
    val totalAmount: Double,
    val transactionId: String? = null, // Nullable for Cash on Delivery per Django migration 0005
    val riderName: String = "Ramesh Kumar (SpeedyRider)",
    val riderPhone: String = "+91 98765 43210",
    val estimatedMinutes: Int = 30,
    val cancellationReason: String? = null
)

data class SupportTicket(
    val id: String,
    val orderId: String,
    val issueType: String,
    val description: String,
    val contactEmail: String,
    val status: String = "Ticket Raised (Staff reviewing)",
    val timestamp: String
)

data class DeliveryLocation(
    val city: String,
    val state: String
)
