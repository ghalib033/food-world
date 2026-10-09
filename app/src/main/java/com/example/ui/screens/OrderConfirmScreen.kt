package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Moped
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.FoodDeliveryViewModel
import com.example.ui.Screen
import com.example.ui.theme.RedGradientEnd
import com.example.ui.theme.RedGradientStart
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VegGreen
import com.example.ui.theme.WarmBg

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.ui.components.VegNonVegBadge

@Composable
fun OrderConfirmScreen(
    orderId: String,
    viewModel: FoodDeliveryViewModel,
    modifier: Modifier = Modifier
) {
    val orders by viewModel.orders.collectAsState()
    val order = orders.find { it.id == orderId } ?: orders.firstOrNull()

    var animated by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        animated = true
    }

    val scale by animateFloatAsState(
        targetValue = if (animated) 1f else 0.4f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "scale"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmBg)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Animated Success Circle
        Box(
            modifier = Modifier
                .size(90.dp)
                .scale(scale)
                .background(
                    Brush.linearGradient(listOf(Color(0xFF43A047), Color(0xFF2E7D32))),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Order Confirmed",
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Order Placed Successfully! 🎉",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Your food is confirmed and being prepared freshly.",
            fontSize = 13.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Order Summary Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Order Reference", fontSize = 11.sp, color = TextSecondary)
                        Text(
                            text = order?.id ?: orderId,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = RedPrimary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(Color(0xFFE8F5E9), RoundedCornerShape(20.dp))
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "STATUS: ${order?.orderStatus?.displayName ?: "CONFIRMED"}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = VegGreen
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Cash on Delivery Announcement
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFE0F2F1), RoundedCornerShape(10.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Payments,
                        contentDescription = "COD",
                        tint = Color(0xFF00695C),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Payment Method: Cash on Delivery",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00695C)
                        )
                        Text(
                            text = "Total to pay on delivery: ₹${String.format("%.2f", order?.totalAmount ?: 0.0)}",
                            fontSize = 11.sp,
                            color = Color(0xFF00796B)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Delivery address
                Text(
                    text = "Delivery Address:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "${order?.customerName ?: "Mohammad Galib"} • ${order?.customerMobile ?: "9820098701"}\n${order?.deliveryAddress}, ${order?.deliveryCity} (${order?.deliveryState}) - ${order?.deliveryPincode ?: "390001"}",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Ordered Food Items List
                Text(
                    text = "Ordered Food Items (${order?.items?.size ?: 0}):",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))

                order?.items?.forEach { cartItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val img = cartItem.foodItem.imageUrls.firstOrNull()
                        if (img != null) {
                            AsyncImage(
                                model = img,
                                contentDescription = cartItem.foodItem.name,
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(6.dp)),
                                contentScale = ContentScale.Crop,
                                error = painterResource(id = cartItem.foodItem.imageResId)
                            )
                        } else {
                            Image(
                                painter = painterResource(id = cartItem.foodItem.imageResId),
                                contentDescription = cartItem.foodItem.name,
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(6.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                VegNonVegBadge(isVeg = cartItem.foodItem.isVeg)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = cartItem.foodItem.name,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextPrimary,
                                    maxLines = 1
                                )
                            }
                            Text(
                                text = "Qty: ${cartItem.quantity} × ₹${String.format("%.2f", cartItem.foodItem.price)}",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }

                        Text(
                            text = "₹${String.format("%.2f", cartItem.totalPrice)}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = RedPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Total Amount highlight
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF9FAFB), RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Total Amount:", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text(
                        text = "₹${String.format("%.2f", order?.totalAmount ?: 0.0)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = RedPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // CTA Buttons: "View My Orders" and "Return to Home" as required
        Button(
            onClick = { viewModel.navigateTo(Screen.OrdersList) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("view_my_orders_button"),
            colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.ReceiptLong, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("View My Orders", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = { viewModel.navigateTo(Screen.Home) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("return_to_home_button"),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = RedPrimary)
        ) {
            Text("Return to Home", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = { viewModel.navigateTo(Screen.OrderTracking(order?.id ?: orderId)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .testTag("track_order_button"),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Moped, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Track Live Order", fontSize = 13.sp, color = TextPrimary)
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
