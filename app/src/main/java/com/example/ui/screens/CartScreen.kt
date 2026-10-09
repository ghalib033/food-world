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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditLocation
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: FoodDeliveryViewModel,
    onBack: () -> Unit,
    listState: androidx.compose.foundation.lazy.LazyListState = androidx.compose.foundation.lazy.rememberLazyListState(),
    modifier: Modifier = Modifier
) {
    val cartItems by viewModel.cartItems.collectAsState()
    val user by viewModel.currentUser.collectAsState()
    val selectedLocation by viewModel.selectedLocation.collectAsState()

    var isEditingAddress by remember { mutableStateOf(false) }
    var addressInput by remember { mutableStateOf(user?.address ?: "Pipe Line Road, Tandalja") }
    var cityInput by remember { mutableStateOf(selectedLocation?.first ?: user?.city ?: "Vadodara") }
    var stateInput by remember { mutableStateOf(selectedLocation?.second ?: user?.state ?: "Gujarat") }

    val subtotal = cartItems.sumOf { it.totalPrice }
    val deliveryFee = if (subtotal > 200 || subtotal == 0.0) 0.0 else 30.0
    val tax = if (subtotal == 0.0) 0.0 else subtotal * 0.05
    val grandTotal = subtotal + deliveryFee + tax

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmBg)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Shopping Cart",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
        )

        if (cartItems.isEmpty()) {
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
                        text = "Your Cart is Empty",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Add delicious items from the menu to place an order.",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = { viewModel.navigateTo(Screen.Home) },
                        colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Explore Food Menu", color = Color.White)
                    }
                }
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Cash on Delivery Announcement
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
                                text = "Cash on Delivery (COD)",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )
                            Text(
                                text = "Pay in cash or UPI directly to our delivery partner when received.",
                                fontSize = 11.sp,
                                color = Color(0xFF2E7D32)
                            )
                        }
                    }
                }

                // Delivery Address Card (matching screenshot Fig 9.2)
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = RedPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Delivery Address",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                }
                                TextButton(onClick = { isEditingAddress = !isEditingAddress }) {
                                    Icon(Icons.Default.EditLocation, contentDescription = null, tint = RedPrimary, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (isEditingAddress) "Done" else "Change",
                                        fontSize = 12.sp,
                                        color = RedPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            if (isEditingAddress) {
                                OutlinedTextField(
                                    value = addressInput,
                                    onValueChange = { addressInput = it },
                                    label = { Text("Street Address") },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedTextField(
                                        value = cityInput,
                                        onValueChange = { cityInput = it },
                                        label = { Text("City") },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    OutlinedTextField(
                                        value = stateInput,
                                        onValueChange = { stateInput = it },
                                        label = { Text("State") },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                }
                            } else {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = user?.fullName ?: "Mohammad Galib",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = user?.mobile ?: "9820098701",
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )
                                Text(
                                    text = addressInput,
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )
                                Text(
                                    text = "$cityInput - ${user?.pincode ?: "390001"} ($stateInput)",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextPrimary
                                )
                            }
                        }
                    }
                }

                // Ordered Items Header
                item {
                    Text(
                        text = "Items (${cartItems.size})",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                // Items List
                items(cartItems, key = { it.foodItem.id }) { item ->
                    CartItemRow(
                        cartItem = item,
                        onUpdateQuantity = { qty -> viewModel.updateCartQuantity(item.foodItem.id, qty) },
                        onRemove = { viewModel.removeFromCart(item.foodItem.id) }
                    )
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
                                        text = "No online payment required now",
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
            }

            // Bottom Sticky Placement Bar (Matching Fig 9.2 button "Place Order (Cash on Delivery)")
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
                            viewModel.navigateTo(Screen.Checkout())
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = RedDark),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("proceed_to_checkout_button")
                    ) {
                        Text(
                            text = "Proceed to Checkout • ₹${String.format("%.2f", grandTotal)}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Back to Menu",
                        fontSize = 13.sp,
                        color = RedPrimary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clickable { viewModel.navigateTo(Screen.Home) }
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    cartItem: CartItem,
    onUpdateQuantity: (Int) -> Unit,
    onRemove: () -> Unit
) {
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
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                val mainImg = cartItem.foodItem.imageUrls.firstOrNull()
                if (mainImg != null) {
                    AsyncImage(
                        model = mainImg,
                        contentDescription = cartItem.foodItem.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = cartItem.foodItem.imageResId),
                        error = painterResource(id = cartItem.foodItem.imageResId)
                    )
                } else {
                    Image(
                        painter = painterResource(id = cartItem.foodItem.imageResId),
                        contentDescription = cartItem.foodItem.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    VegNonVegBadge(isVeg = cartItem.foodItem.isVeg)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = cartItem.foodItem.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        maxLines = 1
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "₹${String.format("%.2f", cartItem.foodItem.price)} each",
                    fontSize = 12.sp,
                    color = TextSecondary
                )

                Text(
                    text = "Total: ₹${String.format("%.2f", cartItem.totalPrice)}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = RedPrimary
                )
            }

            // Quantity stepper
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color(0xFFFFEBEE), RoundedCornerShape(6.dp))
                    .border(1.dp, RedPrimary, RoundedCornerShape(6.dp))
            ) {
                IconButton(
                    onClick = {
                        if (cartItem.quantity > 1) {
                            onUpdateQuantity(cartItem.quantity - 1)
                        } else {
                            onRemove()
                        }
                    },
                    modifier = Modifier.size(28.dp)
                ) {
                    if (cartItem.quantity == 1) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = RedPrimary, modifier = Modifier.size(16.dp))
                    } else {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = RedPrimary, modifier = Modifier.size(16.dp))
                    }
                }

                Text(
                    text = "${cartItem.quantity}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = RedPrimary,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )

                IconButton(
                    onClick = { onUpdateQuantity(cartItem.quantity + 1) },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Increase", tint = RedPrimary, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}
