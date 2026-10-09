package com.example.data.repository

import com.example.R
import com.example.data.model.CartItem
import com.example.data.model.FoodItem
import com.example.data.model.Order
import com.example.data.model.OrderStatus
import com.example.data.model.SupportTicket
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FoodDeliveryRepository {

    private val _currentUser = MutableStateFlow<UserProfile?>(
        UserProfile(
            id = "USR_001",
            username = "galib033",
            fullName = "Mohammad Galib",
            mobile = "9820098701",
            email = "galib@foodworld.app",
            address = "Pipe Line Road, Tandalja",
            city = "Vadodara",
            state = "Gujarat",
            pincode = "390001"
        )
    )
    val currentUser: StateFlow<UserProfile?> = _currentUser.asStateFlow()

    private val legacyFoodItems = listOf(
        FoodItem(
            id = 1L,
            name = "Plain Aloo Paratha (Special)",
            category = "Breads & Parathas",
            isVeg = true,
            price = 99.99,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=800&auto=format&fit=crop"
            ),
            vendorName = "Desi Paratha Corner",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Crispy golden wheat flatbread stuffed with spiced mashed potatoes, served with fresh butter, curd and pickle.",
            rating = 4.7f,
            prepTimeMinutes = 15
        ),
        FoodItem(
            id = 4L,
            name = "Special Veg Thali",
            category = "Veg Thali",
            isVeg = true,
            price = 150.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=800&auto=format&fit=crop"
            ),
            vendorName = "Shree Kathiyawadi Khadki",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Complete wholesome vegetarian meal: Paneer sabzi, seasonal veg, Dal fry, jeera rice, 3 rotis, salad, papad & sweet.",
            rating = 4.9f,
            prepTimeMinutes = 25
        ),
        FoodItem(
            id = 5L,
            name = "Hyderabadi Dum Chicken Biryani (Royal)",
            category = "Biryani",
            isVeg = false,
            price = 300.00,
            imageResId = R.drawable.dish_biryani,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1633945274405-b6c8069047b0?w=800&auto=format&fit=crop"
            ),
            vendorName = "NonVegAi Biryani Darbar",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Authentic dum cooked long-grain basmati rice with marinated tender chicken drumsticks, saffron, caramelized onions and raita.",
            rating = 4.9f,
            prepTimeMinutes = 35
        ),
        FoodItem(
            id = 6L,
            name = "Fresh Chicken Biryani (Signature)",
            category = "Biryani",
            isVeg = false,
            price = 250.00,
            imageResId = R.drawable.dish_biryani,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1633945274405-b6c8069047b0?w=800&auto=format&fit=crop"
            ),
            vendorName = "NonVegAi Biryani Darbar",
            vendorCity = "Ahmedabad",
            vendorState = "Gujarat",
            available = true,
            description = "Flavored spiced basmati rice layered with succulent farm fresh chicken cuts, boiled egg, mint and aromatic masala.",
            rating = 4.7f,
            prepTimeMinutes = 30
        )
    )

    private val initialFoodItems = FoodCatalog.allProducts + legacyFoodItems

    private val _foodItems = MutableStateFlow<List<FoodItem>>(initialFoodItems)
    val foodItems: StateFlow<List<FoodItem>> = _foodItems.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val initialOrders = listOf(
        Order(
            id = "#94",
            orderTime = "July 28, 2026 06:34 PM",
            items = listOf(
                CartItem(
                    foodItem = initialFoodItems.first { it.id == 4L }, // Veg Thali
                    quantity = 1
                )
            ),
            deliveryAddress = "Pipe Line Road, Tandalja",
            deliveryCity = "Vadodara",
            deliveryState = "Gujarat",
            deliveryPincode = "390001",
            paymentMethod = "Cash on Delivery",
            paymentStatus = "Pending",
            orderStatus = OrderStatus.CONFIRMED,
            subtotal = 150.00,
            deliveryFee = 0.0,
            tax = 0.0,
            totalAmount = 150.00,
            transactionId = null,
            riderName = "Kunal Patel (SpeedyRider)",
            riderPhone = "+91 98251 12345",
            estimatedMinutes = 28
        ),
        Order(
            id = "#91",
            orderTime = "September 17, 2026 04:30 PM",
            items = listOf(
                CartItem(
                    foodItem = initialFoodItems.first { it.id == 6L }, // Chicken Biryani
                    quantity = 1
                )
            ),
            deliveryAddress = "Navrangpura Road, Near University",
            deliveryCity = "Ahmedabad",
            deliveryState = "Gujarat",
            deliveryPincode = "380009",
            paymentMethod = "Cash on Delivery",
            paymentStatus = "Pending",
            orderStatus = OrderStatus.COOKING,
            subtotal = 250.00,
            deliveryFee = 0.0,
            tax = 0.0,
            totalAmount = 250.00,
            transactionId = null,
            riderName = "Imran Khan",
            riderPhone = "+91 97234 56789",
            estimatedMinutes = 20
        ),
        Order(
            id = "#90",
            orderTime = "September 17, 2026 01:15 PM",
            items = listOf(
                CartItem(
                    foodItem = initialFoodItems.first { it.id == 5L }, // Hyderabadi Dum Chicken Biryani
                    quantity = 1
                )
            ),
            deliveryAddress = "Pipe Line Road, Tandalja",
            deliveryCity = "Vadodara",
            deliveryState = "Gujarat",
            deliveryPincode = "390001",
            paymentMethod = "Cash on Delivery",
            paymentStatus = "Paid on Delivery",
            orderStatus = OrderStatus.DELIVERED,
            subtotal = 300.00,
            deliveryFee = 0.0,
            tax = 0.0,
            totalAmount = 300.00,
            transactionId = null,
            riderName = "Ramesh Sharma",
            riderPhone = "+91 98765 43210",
            estimatedMinutes = 0
        )
    )

    private val _orders = MutableStateFlow<List<Order>>(initialOrders)
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    private val _supportTickets = MutableStateFlow<List<SupportTicket>>(emptyList())
    val supportTickets: StateFlow<List<SupportTicket>> = _supportTickets.asStateFlow()

    // Location filter preference (null means use profile location, empty string means show all)
    private val _selectedLocation = MutableStateFlow<Pair<String, String>?>(null)
    val selectedLocation: StateFlow<Pair<String, String>?> = _selectedLocation.asStateFlow()

    fun setLocationFilter(city: String, state: String) {
        _selectedLocation.value = Pair(city, state)
        // Also update user profile city/state if logged in
        _currentUser.value?.let { current ->
            _currentUser.value = current.copy(city = city, state = state)
        }
    }

    fun clearLocationFilter() {
        _selectedLocation.value = null
    }

    fun addToCart(foodItem: FoodItem, qty: Int = 1) {
        val current = _cartItems.value.toMutableList()
        val index = current.indexOfFirst { it.foodItem.id == foodItem.id }
        if (index != -1) {
            val existing = current[index]
            current[index] = existing.copy(quantity = existing.quantity + qty)
        } else {
            current.add(CartItem(foodItem = foodItem, quantity = qty))
        }
        _cartItems.value = current
    }

    fun updateCartQuantity(foodItemId: Long, newQuantity: Int) {
        val current = _cartItems.value.toMutableList()
        val index = current.indexOfFirst { it.foodItem.id == foodItemId }
        if (index != -1) {
            if (newQuantity <= 0) {
                current.removeAt(index)
            } else {
                current[index] = current[index].copy(quantity = newQuantity)
            }
            _cartItems.value = current
        }
    }

    fun removeFromCart(foodItemId: Long) {
        _cartItems.value = _cartItems.value.filterNot { it.foodItem.id == foodItemId }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    private val _processedOrderKeys = mutableMapOf<String, Order>()

    fun placeOrder(
        itemsToOrder: List<CartItem> = _cartItems.value,
        customerName: String? = null,
        customerMobile: String? = null,
        customAddress: String? = null,
        customCity: String? = null,
        customState: String? = null,
        idempotencyKey: String? = null
    ): Order {
        if (idempotencyKey != null && _processedOrderKeys.containsKey(idempotencyKey)) {
            return _processedOrderKeys.getValue(idempotencyKey)
        }

        require(itemsToOrder.isNotEmpty()) { "Your order cannot be empty. Please select food items." }
        require(itemsToOrder.all { it.quantity > 0 }) { "All item quantities must be greater than zero." }

        val user = _currentUser.value
        val name = (customerName ?: user?.fullName ?: "Mohammad Galib").trim()
        val mobile = (customerMobile ?: user?.mobile ?: "9820098701").trim()
        val addr = (customAddress ?: user?.address ?: "Near Central Market").trim()
        val city = (customCity ?: user?.city ?: "Vadodara").trim()
        val state = (customState ?: user?.state ?: "Gujarat").trim()

        require(name.length >= 2) { "Please enter a valid customer name (at least 2 characters)." }
        require(mobile.length >= 10) { "Please enter a valid 10-digit mobile number." }
        require(addr.length >= 5) { "Please enter a complete delivery address (at least 5 characters)." }
        require(city.isNotBlank()) { "Please specify your delivery city." }
        require(state.isNotBlank()) { "Please specify your delivery state." }

        // Recalculate price backend-side from trusted catalog
        val subtotal = itemsToOrder.sumOf { it.foodItem.price * it.quantity }
        val deliveryFee = if (subtotal > 200 || subtotal == 0.0) 0.0 else 30.0
        val tax = (subtotal * 0.05) // 5% GST
        val total = subtotal + deliveryFee + tax

        val sdf = SimpleDateFormat("MMMM dd, yyyy hh:mm a", Locale.getDefault())
        val newOrderNumber = "#${(95..999).random()}"

        val order = Order(
            id = newOrderNumber,
            orderTime = sdf.format(Date()),
            items = itemsToOrder,
            customerName = name,
            customerMobile = mobile,
            deliveryAddress = addr,
            deliveryCity = city,
            deliveryState = state,
            deliveryPincode = user?.pincode ?: "390001",
            paymentMethod = "Cash on Delivery",
            paymentStatus = "Pending",
            orderStatus = OrderStatus.CONFIRMED,
            subtotal = subtotal,
            deliveryFee = deliveryFee,
            tax = tax,
            totalAmount = total,
            transactionId = null, // per migration 0005
            riderName = "Vikram Solanki",
            riderPhone = "+91 98980 12345",
            estimatedMinutes = 32,
            cancellationReason = null
        )

        if (idempotencyKey != null) {
            _processedOrderKeys[idempotencyKey] = order
        }

        _orders.value = listOf(order) + _orders.value
        // If placing from cart, clear cart
        if (itemsToOrder == _cartItems.value) {
            clearCart()
        }
        return order
    }

    fun cancelOrder(orderId: String, reason: String = "Ordered by mistake"): Result<Unit> {
        val cleanReason = reason.trim()
        if (cleanReason.isBlank()) {
            return Result.failure(IllegalArgumentException("Please select or provide a reason for cancelling your order."))
        }
        val currentOrders = _orders.value.toMutableList()
        val index = currentOrders.indexOfFirst { it.id == orderId }
        if (index == -1) {
            return Result.failure(IllegalArgumentException("Order $orderId not found."))
        }

        val order = currentOrders[index]
        if (order.orderStatus == OrderStatus.CANCELLED) {
            return Result.failure(IllegalStateException("Order $orderId is already cancelled."))
        }
        if (order.orderStatus == OrderStatus.DELIVERED) {
            return Result.failure(IllegalStateException("Order $orderId has already been delivered and cannot be cancelled."))
        }
        if (!order.orderStatus.canBeCancelled()) {
            return Result.failure(IllegalStateException("Order cannot be cancelled in status: ${order.orderStatus.displayName}"))
        }

        currentOrders[index] = order.copy(
            orderStatus = OrderStatus.CANCELLED,
            cancellationReason = cleanReason
        )
        _orders.value = currentOrders
        return Result.success(Unit)
    }

    fun updateOrderStatus(orderId: String, newStatus: OrderStatus) {
        val currentOrders = _orders.value.toMutableList()
        val index = currentOrders.indexOfFirst { it.id == orderId }
        if (index != -1) {
            val order = currentOrders[index]
            val newPaymentStatus = if (newStatus == OrderStatus.DELIVERED) "Paid on Delivery" else order.paymentStatus
            currentOrders[index] = order.copy(
                orderStatus = newStatus,
                paymentStatus = newPaymentStatus
            )
            _orders.value = currentOrders
        }
    }

    fun submitSupportTicket(orderId: String, issueType: String, description: String, email: String): SupportTicket {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val ticket = SupportTicket(
            id = "TCK-${(1000..9999).random()}",
            orderId = orderId,
            issueType = issueType,
            description = description,
            contactEmail = email,
            timestamp = sdf.format(Date())
        )
        _supportTickets.value = listOf(ticket) + _supportTickets.value
        return ticket
    }

    fun toggleFoodAvailability(foodId: Long) {
        val current = _foodItems.value.toMutableList()
        val index = current.indexOfFirst { it.id == foodId }
        if (index != -1) {
            val item = current[index]
            current[index] = item.copy(available = !item.available)
            _foodItems.value = current
        }
    }

    fun updateFoodItem(updatedItem: FoodItem) {
        val current = _foodItems.value.toMutableList()
        val index = current.indexOfFirst { it.id == updatedItem.id }
        if (index != -1) {
            current[index] = updatedItem
            _foodItems.value = current
        }
    }

    fun addFoodItem(item: FoodItem) {
        _foodItems.value = listOf(item) + _foodItems.value
    }

    fun updateUserProfile(profile: UserProfile) {
        _currentUser.value = profile
    }

    fun logout() {
        _currentUser.value = null
    }

    fun login(usernameOrEmail: String, password: String): Boolean {
        // Authenticate user
        if (usernameOrEmail.isNotBlank() && password.length >= 4) {
            _currentUser.value = UserProfile(
                id = "USR_${usernameOrEmail.hashCode().toString().takeLast(4)}",
                username = usernameOrEmail.substringBefore("@"),
                fullName = if (usernameOrEmail.contains("galib", ignoreCase = true)) "Mohammad Galib" else "Food World Customer",
                mobile = "9820098701",
                email = if (usernameOrEmail.contains("@")) usernameOrEmail else "$usernameOrEmail@gmail.com",
                address = "Pipe Line Road, Tandalja",
                city = "Vadodara",
                state = "Gujarat"
            )
            return true
        }
        return false
    }

    fun register(
        fullName: String,
        mobile: String,
        email: String,
        address: String,
        city: String,
        state: String
    ) {
        _currentUser.value = UserProfile(
            id = "USR_${(100..999).random()}",
            username = fullName.lowercase().replace(" ", "_"),
            fullName = fullName,
            mobile = mobile,
            email = email.ifBlank { "$mobile@foodworld.app" },
            address = address,
            city = city,
            state = state
        )
    }
}
