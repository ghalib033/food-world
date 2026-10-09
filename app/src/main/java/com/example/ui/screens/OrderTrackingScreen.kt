package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Moped
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.OrderStatus
import com.example.ui.FoodDeliveryViewModel
import com.example.ui.Screen
import com.example.ui.theme.NonVegRed
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.StatusCooking
import com.example.ui.theme.StatusDelivered
import com.example.ui.theme.StatusOutForDelivery
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VegGreen
import com.example.ui.theme.WarmBg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTrackingScreen(
    orderId: String,
    viewModel: FoodDeliveryViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val orders by viewModel.orders.collectAsState()
    val order = orders.find { it.id == orderId } ?: orders.firstOrNull()

    var showCancelDialog by remember { mutableStateOf(false) }

    if (order == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Order not found", color = TextSecondary)
        }
        return
    }

    if (showCancelDialog) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = { Text("Cancel Order?", fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "Are you sure you want to cancel order ${order.id}? " +
                            "Per cancellation rules, orders can only be cancelled before delivery."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.cancelOrder(order.id)
                        showCancelDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NonVegRed)
                ) {
                    Text("Confirm Cancel", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCancelDialog = false }) {
                    Text("Keep Order")
                }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmBg)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Track Order ${order.id}",
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
            // Live Status Header Banner
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
                            Text("Current Status", fontSize = 12.sp, color = TextSecondary)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(
                                            when (order.orderStatus) {
                                                OrderStatus.DELIVERED -> StatusDelivered
                                                OrderStatus.CANCELLED -> NonVegRed
                                                OrderStatus.OUT_FOR_DELIVERY -> StatusOutForDelivery
                                                OrderStatus.COOKING -> StatusCooking
                                                OrderStatus.CONFIRMED -> VegGreen
                                            },
                                            CircleShape
                                        )
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = order.orderStatus.displayName,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = when (order.orderStatus) {
                                        OrderStatus.DELIVERED -> StatusDelivered
                                        OrderStatus.CANCELLED -> NonVegRed
                                        OrderStatus.OUT_FOR_DELIVERY -> StatusOutForDelivery
                                        OrderStatus.COOKING -> StatusCooking
                                        OrderStatus.CONFIRMED -> RedPrimary
                                    }
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text("Placed at", fontSize = 11.sp, color = TextSecondary)
                            Text(order.orderTime, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Progress bar
                    val currentStep = when (order.orderStatus) {
                        OrderStatus.CONFIRMED -> 0
                        OrderStatus.COOKING -> 1
                        OrderStatus.OUT_FOR_DELIVERY -> 2
                        OrderStatus.DELIVERED -> 3
                        OrderStatus.CANCELLED -> -1
                    }

                    if (order.orderStatus != OrderStatus.CANCELLED) {
                        Text(
                            text = when (order.orderStatus) {
                                OrderStatus.CONFIRMED -> "Order received by kitchen. Food preparation starting."
                                OrderStatus.COOKING -> "Chef is preparing your fresh meal with authentic spices."
                                OrderStatus.OUT_FOR_DELIVERY -> "Rider picked up food and is on the way!"
                                OrderStatus.DELIVERED -> "Food safely delivered. Enjoy your meal!"
                                else -> ""
                            },
                            fontSize = 13.sp,
                            color = Color(0xFF4B5563)
                        )
                    } else {
                        Text(
                            text = "This order was cancelled. No payment was collected.",
                            fontSize = 13.sp,
                            color = NonVegRed,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Timeline Steps
            if (order.orderStatus != OrderStatus.CANCELLED) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text("Order Journey", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Spacer(modifier = Modifier.height(14.dp))

                        TimelineStep(
                            icon = Icons.Default.CheckCircle,
                            title = "Order Confirmed",
                            subtitle = "Cash on Delivery recorded (₹${String.format("%.2f", order.totalAmount)})",
                            isCompleted = order.orderStatus.stepIndex >= 0,
                            isCurrent = order.orderStatus == OrderStatus.CONFIRMED,
                            showLine = true
                        )

                        TimelineStep(
                            icon = Icons.Default.Restaurant,
                            title = "Cooking in Kitchen",
                            subtitle = "Chef is preparing dishes fresh per order",
                            isCompleted = order.orderStatus.stepIndex >= 1,
                            isCurrent = order.orderStatus == OrderStatus.COOKING,
                            showLine = true
                        )

                        TimelineStep(
                            icon = Icons.Default.Moped,
                            title = "Out for Delivery",
                            subtitle = "Rider: ${order.riderName}",
                            isCompleted = order.orderStatus.stepIndex >= 2,
                            isCurrent = order.orderStatus == OrderStatus.OUT_FOR_DELIVERY,
                            showLine = true
                        )

                        TimelineStep(
                            icon = Icons.Default.Fastfood,
                            title = "Delivered to Doorstep",
                            subtitle = "Handed to customer; COD paid upon receipt",
                            isCompleted = order.orderStatus.stepIndex >= 3,
                            isCurrent = order.orderStatus == OrderStatus.DELIVERED,
                            showLine = false
                        )
                    }
                }
            }

            // Rider Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color(0xFFFFEBEE), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Moped, contentDescription = null, tint = RedPrimary)
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(order.riderName, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text(order.riderPhone, fontSize = 12.sp, color = TextSecondary)
                        Text("Vehicle: Honda Activa (GJ-06-AA-4021)", fontSize = 11.sp, color = TextSecondary)
                    }
                    IconButton(
                        onClick = { /* Call action */ },
                        modifier = Modifier
                            .background(Color(0xFFE8F5E9), CircleShape)
                            .size(40.dp)
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = "Call", tint = VegGreen, modifier = Modifier.size(20.dp))
                    }
                }
            }

            // Test Simulation Tool (Advance Status)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Testing Lab Simulator (Practical 10 & 11)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Advance order through its lifecycle to test real-time state changes and cancellation rules.",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                val nextStatus = when (order.orderStatus) {
                                    OrderStatus.CONFIRMED -> OrderStatus.COOKING
                                    OrderStatus.COOKING -> OrderStatus.OUT_FOR_DELIVERY
                                    OrderStatus.OUT_FOR_DELIVERY -> OrderStatus.DELIVERED
                                    else -> OrderStatus.CONFIRMED
                                }
                                viewModel.updateOrderStatus(order.id, nextStatus)
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Next Step", fontSize = 12.sp)
                        }

                        if (order.orderStatus.canBeCancelled()) {
                            Button(
                                onClick = { showCancelDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = NonVegRed),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("cancel_order_button"),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.Cancel, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Cancel", fontSize = 12.sp, color = Color.White)
                            }
                        }
                    }
                }
            }
        }

        // Bottom Bar (View Details / Back)
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.navigateTo(Screen.OrderDetail(order.id)) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("View Full Receipt", fontSize = 13.sp)
                }

                Button(
                    onClick = { viewModel.navigateTo(Screen.OrdersList) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("All Orders", fontSize = 13.sp, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun TimelineStep(
    icon: ImageVector,
    title: String,
    subtitle: String,
    isCompleted: Boolean,
    isCurrent: Boolean,
    showLine: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(
                        when {
                            isCurrent -> RedPrimary
                            isCompleted -> VegGreen
                            else -> Color(0xFFE0E0E0)
                        },
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }

            if (showLine) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(38.dp)
                        .background(if (isCompleted) VegGreen else Color(0xFFE0E0E0))
                )
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.padding(bottom = if (showLine) 16.dp else 4.dp)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = if (isCurrent || isCompleted) FontWeight.Bold else FontWeight.Medium,
                color = if (isCurrent) RedPrimary else if (isCompleted) TextPrimary else TextSecondary
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = TextSecondary
            )
        }
    }
}
