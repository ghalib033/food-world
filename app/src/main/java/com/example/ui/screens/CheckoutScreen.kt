package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.CartItem
import com.example.ui.FoodDeliveryViewModel
import com.example.ui.Screen
import com.example.ui.components.VegNonVegBadge
import com.example.ui.theme.RedDark
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarmBg
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    viewModel: FoodDeliveryViewModel,
    quickFoodId: Long? = null,
    initialQty: Int = 1,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val user by viewModel.currentUser.collectAsState()
    val allFood by viewModel.allFoodItems.collectAsState()
    val cartItemsState by viewModel.cartItems.collectAsState()
    val selectedLocation by viewModel.selectedLocation.collectAsState()

    // Initialize checkout items: either single quick-order product or current cart
    val checkoutItems = remember(quickFoodId) {
        val list = mutableStateListOf<CartItem>()
        if (quickFoodId != null) {
            val food = allFood.find { it.id == quickFoodId }
            if (food != null) {
                list.add(CartItem(foodItem = food, quantity = initialQty))
            }
        } else {
            list.addAll(cartItemsState)
        }
        list
    }

    // Customer & Delivery input states
    var customerName by remember { mutableStateOf(user?.fullName ?: "Mohammad Galib") }
    var customerMobile by remember { mutableStateOf(user?.mobile ?: "9820098701") }
    var deliveryAddress by remember { mutableStateOf(user?.address ?: "Pipe Line Road, Tandalja") }
    var deliveryCity by remember { mutableStateOf(selectedLocation?.first ?: user?.city ?: "Vadodara") }
    var deliveryState by remember { mutableStateOf(selectedLocation?.second ?: user?.state ?: "Gujarat") }

    // Validation errors
    var nameError by remember { mutableStateOf<String?>(null) }
    var mobileError by remember { mutableStateOf<String?>(null) }
    var addressError by remember { mutableStateOf<String?>(null) }
    var cityError by remember { mutableStateOf<String?>(null) }
    var stateError by remember { mutableStateOf<String?>(null) }
    var generalError by remember { mutableStateOf<String?>(null) }

    // Duplicate submission prevention & idempotency key
    var isSubmitting by remember { mutableStateOf(false) }
    val idempotencyKey = remember { UUID.randomUUID().toString() }

    // Pricing calculation based on current checkout items
    val subtotal = checkoutItems.sumOf { it.totalPrice }
    val deliveryFee = if (subtotal > 200.0 || subtotal == 0.0) 0.0 else 30.0
    val tax = if (subtotal == 0.0) 0.0 else subtotal * 0.05
    val grandTotal = subtotal + deliveryFee + tax

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmBg)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Checkout",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Review details before confirming",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = onBack, modifier = Modifier.testTag("checkout_back_button")) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
        )

        if (checkoutItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .background(Color(0xFFFFEBEE), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = RedPrimary,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No Items in Checkout",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Please add items to your cart before proceeding to checkout.",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = { viewModel.navigateTo(Screen.Home) },
                        colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Return to Menu", color = Color.White)
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Payment Method Announcement (Cash on Delivery)
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFE8F5E9), RoundedCornerShape(12.dp))
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(Color(0xFFC8E6C9), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Payments, contentDescription = null, tint = Color(0xFF1B5E20))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Payment Method: Cash on Delivery (COD)",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )
                            Text(
                                text = "Pay in cash or UPI directly when food is delivered. No upfront payment.",
                                fontSize = 11.sp,
                                color = Color(0xFF2E7D32)
                            )
                        }
                    }
                }

                // Customer and Delivery Details Card with live form validation
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Person, contentDescription = null, tint = RedPrimary, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Customer & Delivery Details",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))

                            // Customer Name
                            OutlinedTextField(
                                value = customerName,
                                onValueChange = {
                                    customerName = it
                                    if (it.trim().length >= 2) nameError = null
                                },
                                label = { Text("Customer Full Name *") },
                                isError = nameError != null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("checkout_name_input"),
                                shape = RoundedCornerShape(8.dp),
                                singleLine = true
                            )
                            if (nameError != null) {
                                Text(nameError ?: "", color = RedPrimary, fontSize = 11.sp, modifier = Modifier.padding(start = 4.dp, top = 2.dp))
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Mobile Number
                            OutlinedTextField(
                                value = customerMobile,
                                onValueChange = {
                                    customerMobile = it
                                    if (it.trim().length >= 10) mobileError = null
                                },
                                label = { Text("Mobile Number *") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                isError = mobileError != null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("checkout_mobile_input"),
                                shape = RoundedCornerShape(8.dp),
                                singleLine = true
                            )
                            if (mobileError != null) {
                                Text(mobileError ?: "", color = RedPrimary, fontSize = 11.sp, modifier = Modifier.padding(start = 4.dp, top = 2.dp))
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Delivery Address
                            OutlinedTextField(
                                value = deliveryAddress,
                                onValueChange = {
                                    deliveryAddress = it
                                    if (it.trim().length >= 5) addressError = null
                                },
                                label = { Text("Delivery Address (House/Street/Area) *") },
                                isError = addressError != null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("checkout_address_input"),
                                shape = RoundedCornerShape(8.dp)
                            )
                            if (addressError != null) {
                                Text(addressError ?: "", color = RedPrimary, fontSize = 11.sp, modifier = Modifier.padding(start = 4.dp, top = 2.dp))
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // City and State Row
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Column(modifier = Modifier.weight(1f)) {
                                    OutlinedTextField(
                                        value = deliveryCity,
                                        onValueChange = {
                                            deliveryCity = it
                                            if (it.isNotBlank()) cityError = null
                                        },
                                        label = { Text("City *") },
                                        isError = cityError != null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("checkout_city_input"),
                                        shape = RoundedCornerShape(8.dp),
                                        singleLine = true
                                    )
                                    if (cityError != null) {
                                        Text(cityError ?: "", color = RedPrimary, fontSize = 11.sp, modifier = Modifier.padding(start = 4.dp, top = 2.dp))
                                    }
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    OutlinedTextField(
                                        value = deliveryState,
                                        onValueChange = {
                                            deliveryState = it
                                            if (it.isNotBlank()) stateError = null
                                        },
                                        label = { Text("State *") },
                                        isError = stateError != null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("checkout_state_input"),
                                        shape = RoundedCornerShape(8.dp),
                                        singleLine = true
                                    )
                                    if (stateError != null) {
                                        Text(stateError ?: "", color = RedPrimary, fontSize = 11.sp, modifier = Modifier.padding(start = 4.dp, top = 2.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                // Selected Food Items Review
                item {
                    Text(
                        text = "Selected Items (${checkoutItems.size})",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                items(checkoutItems, key = { it.foodItem.id }) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Product Image
                            val mainImg = item.foodItem.imageUrls.firstOrNull()
                            if (mainImg != null) {
                                AsyncImage(
                                    model = mainImg,
                                    contentDescription = item.foodItem.name,
                                    modifier = Modifier
                                        .size(68.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop,
                                    error = painterResource(id = item.foodItem.imageResId)
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = item.foodItem.imageResId),
                                    contentDescription = item.foodItem.name,
                                    modifier = Modifier
                                        .size(68.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    VegNonVegBadge(isVeg = item.foodItem.isVeg)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = item.foodItem.name,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary,
                                        maxLines = 1
                                    )
                                }

                                Text(
                                    text = "₹${String.format("%.2f", item.foodItem.price)} each",
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "Total: ₹${String.format("%.2f", item.totalPrice)}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = RedPrimary
                                )
                            }

                            // Stepper & Remove controls
                            Column(horizontalAlignment = Alignment.End) {
                                IconButton(
                                    onClick = {
                                        val idx = checkoutItems.indexOfFirst { it.foodItem.id == item.foodItem.id }
                                        if (idx != -1) checkoutItems.removeAt(idx)
                                        // Also sync with cart if came from cart
                                        if (quickFoodId == null) {
                                            viewModel.removeFromCart(item.foodItem.id)
                                        }
                                    },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = "Remove", tint = Color.Gray, modifier = Modifier.size(18.dp))
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .background(Color(0xFFFFEBEE), RoundedCornerShape(8.dp))
                                        .border(1.dp, RedPrimary, RoundedCornerShape(8.dp))
                                        .padding(horizontal = 4.dp)
                                ) {
                                    IconButton(
                                        onClick = {
                                            val idx = checkoutItems.indexOfFirst { it.foodItem.id == item.foodItem.id }
                                            if (idx != -1) {
                                                if (item.quantity > 1) {
                                                    checkoutItems[idx] = item.copy(quantity = item.quantity - 1)
                                                    if (quickFoodId == null) {
                                                        viewModel.updateCartQuantity(item.foodItem.id, item.quantity - 1)
                                                    }
                                                } else {
                                                    checkoutItems.removeAt(idx)
                                                    if (quickFoodId == null) {
                                                        viewModel.removeFromCart(item.foodItem.id)
                                                    }
                                                }
                                            }
                                        },
                                        modifier = Modifier.size(26.dp)
                                    ) {
                                        Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = RedPrimary, modifier = Modifier.size(14.dp))
                                    }

                                    Text(
                                        text = "${item.quantity}",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = RedPrimary,
                                        modifier = Modifier.padding(horizontal = 6.dp)
                                    )

                                    IconButton(
                                        onClick = {
                                            val idx = checkoutItems.indexOfFirst { it.foodItem.id == item.foodItem.id }
                                            if (idx != -1) {
                                                checkoutItems[idx] = item.copy(quantity = item.quantity + 1)
                                                if (quickFoodId == null) {
                                                    viewModel.updateCartQuantity(item.foodItem.id, item.quantity + 1)
                                                }
                                            }
                                        },
                                        modifier = Modifier.size(26.dp)
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = "Increase", tint = RedPrimary, modifier = Modifier.size(14.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                // Bill Details Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Bill Details",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Item Total", fontSize = 13.sp, color = TextSecondary)
                                Text("₹${String.format("%.2f", subtotal)}", fontSize = 13.sp, color = TextPrimary)
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Delivery Partner Fee", fontSize = 13.sp, color = TextSecondary)
                                Text(
                                    text = if (deliveryFee == 0.0) "FREE" else "₹${String.format("%.2f", deliveryFee)}",
                                    fontSize = 13.sp,
                                    fontWeight = if (deliveryFee == 0.0) FontWeight.Bold else FontWeight.Normal,
                                    color = if (deliveryFee == 0.0) Color(0xFF2E7D32) else TextPrimary
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Govt Taxes & Restaurant GST (5%)", fontSize = 13.sp, color = TextSecondary)
                                Text("₹${String.format("%.2f", tax)}", fontSize = 13.sp, color = TextPrimary)
                            }

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 12.dp),
                                color = Color(0xFFEEEEEE)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "To Pay (Cash on Delivery)",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = "Pay on food arrival",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                }

                                Text(
                                    text = "₹${String.format("%.2f", grandTotal)}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    color = RedPrimary
                                )
                            }
                        }
                    }
                }

                if (generalError != null) {
                    item {
                        Text(
                            text = generalError ?: "",
                            color = RedPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
            }

            // Bottom Sticky "Confirm Order" Bar
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            if (isSubmitting) return@Button

                            // Client validations
                            var hasError = false
                            if (customerName.trim().length < 2) {
                                nameError = "Name must be at least 2 characters."
                                hasError = true
                            } else {
                                nameError = null
                            }

                            if (customerMobile.trim().length < 10) {
                                mobileError = "Please enter a valid 10-digit mobile number."
                                hasError = true
                            } else {
                                mobileError = null
                            }

                            if (deliveryAddress.trim().length < 5) {
                                addressError = "Please enter full address (min 5 characters)."
                                hasError = true
                            } else {
                                addressError = null
                            }

                            if (deliveryCity.trim().isBlank()) {
                                cityError = "City cannot be empty."
                                hasError = true
                            } else {
                                cityError = null
                            }

                            if (deliveryState.trim().isBlank()) {
                                stateError = "State cannot be empty."
                                hasError = true
                            } else {
                                stateError = null
                            }

                            if (checkoutItems.isEmpty()) {
                                generalError = "Cannot place order: your item list is empty."
                                hasError = true
                            }

                            if (hasError) return@Button

                            isSubmitting = true
                            generalError = null

                            viewModel.confirmCheckoutOrder(
                                items = checkoutItems.toList(),
                                customerName = customerName.trim(),
                                customerMobile = customerMobile.trim(),
                                address = deliveryAddress.trim(),
                                city = deliveryCity.trim(),
                                state = deliveryState.trim(),
                                idempotencyKey = idempotencyKey,
                                onSuccess = {
                                    isSubmitting = false
                                }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = RedDark),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !isSubmitting,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("confirm_order_button")
                    ) {
                        if (isSubmitting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Confirming Order...", color = Color.White, fontWeight = FontWeight.Bold)
                        } else {
                            Text(
                                text = "Confirm Order • ₹${String.format("%.2f", grandTotal)}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Cancel & Return to Cart",
                        fontSize = 13.sp,
                        color = RedPrimary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clickable { onBack() }
                            .padding(4.dp)
                            .testTag("cancel_checkout_text")
                    )
                }
            }
        }
    }
}
