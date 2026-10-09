package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Moped
import androidx.compose.material.icons.filled.Payments
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.data.model.OrderStatus
import com.example.ui.FoodDeliveryViewModel
import com.example.ui.Screen
import com.example.ui.components.VegNonVegBadge
import com.example.ui.theme.NonVegRed
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VegGreen
import com.example.ui.theme.WarmBg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(
    orderId: String,
    viewModel: FoodDeliveryViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val orders by viewModel.orders.collectAsState()
    val order = orders.find { it.id == orderId } ?: orders.firstOrNull()

    if (order == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Order details not found", color = TextSecondary)
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmBg)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Order Details ${order.id}",
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
            actions = {
                IconButton(onClick = { viewModel.navigateTo(Screen.HelpSupport(order.id)) }) {
                    Icon(Icons.AutoMirrored.Filled.Help, contentDescription = "Help", tint = RedPrimary)
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Order Information Card (Matching Fig 9.4)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Order Information",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Order ID:", fontSize = 13.sp, color = TextSecondary)
                        Text(order.id, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = RedPrimary)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Status:", fontSize = 13.sp, color = TextSecondary)
                        Box(
                            modifier = Modifier
                                .background(
                                    if (order.orderStatus == OrderStatus.DELIVERED) Color(0xFFE8F5E9) else Color(0xFFFFF3E0),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = order.orderStatus.displayName,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (order.orderStatus == OrderStatus.DELIVERED) VegGreen else Color(0xFFE65100)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Order Date:", fontSize = 13.sp, color = TextSecondary)
                        Text(order.orderTime, fontSize = 12.sp, color = TextPrimary)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Payment Method:", fontSize = 13.sp, color = TextSecondary)
                        Text(order.paymentMethod, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Payment Status:", fontSize = 13.sp, color = TextSecondary)
                        Text(order.paymentStatus, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = if (order.paymentStatus == "Pending") Color(0xFFF57C00) else VegGreen)
                    }

                    if (order.transactionId != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Transaction ID:", fontSize = 13.sp, color = TextSecondary)
                            Text(order.transactionId, fontSize = 12.sp, color = TextSecondary)
                        }
                    }
                }
            }

            // Delivery Address Card (Matching Fig 9.4)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = RedPrimary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Delivery Address",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${order.deliveryAddress}\n${order.deliveryCity} - ${order.deliveryPincode}\nState: ${order.deliveryState}",
                        fontSize = 13.sp,
                        color = Color(0xFF4B5563),
                        lineHeight = 18.sp
                    )
                }
            }

            // Order Items Card (Matching Fig 9.4)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Order Items",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    order.items.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                val mainImg = item.foodItem.imageUrls.firstOrNull()
                                if (mainImg != null) {
                                    AsyncImage(
                                        model = mainImg,
                                        contentDescription = item.foodItem.name,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop,
                                        placeholder = painterResource(id = item.foodItem.imageResId),
                                        error = painterResource(id = item.foodItem.imageResId)
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = item.foodItem.imageResId),
                                        contentDescription = item.foodItem.name,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = item.foodItem.name,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    VegNonVegBadge(isVeg = item.foodItem.isVeg)
                                }

                                Text(
                                    text = item.foodItem.description,
                                    fontSize = 11.sp,
                                    color = TextSecondary,
                                    maxLines = 1
                                )

                                Text(
                                    text = "Quantity: ${item.quantity} × ₹${String.format("%.2f", item.foodItem.price)} = ₹${String.format("%.2f", item.totalPrice)}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = RedPrimary
                                )
                            }
                        }
                        HorizontalDivider(color = Color(0xFFF3F4F6))
                    }
                }
            }

            // Green Total Amount Bar (Identical to Fig 9.4 screenshot!)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2E7D32), RoundedCornerShape(10.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Total Amount: ₹${String.format("%.2f", order.totalAmount)}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { viewModel.navigateTo(Screen.OrderTracking(order.id)) },
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp)
                        .testTag("track_order_cta"),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Moped, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Track Order", color = Color.White, fontWeight = FontWeight.Bold)
                }

                if (order.orderStatus.canBeCancelled()) {
                    OutlinedButton(
                        onClick = { viewModel.cancelOrder(order.id) },
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = NonVegRed)
                    ) {
                        Text("Cancel Order", color = NonVegRed, fontWeight = FontWeight.Bold)
                    }
                }
            }

            OutlinedButton(
                onClick = { viewModel.navigateTo(Screen.OrdersList) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Back to Orders", color = TextPrimary)
            }

            OutlinedButton(
                onClick = { viewModel.navigateTo(Screen.Home) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Continue Shopping", color = TextPrimary)
            }
        }
    }
}
