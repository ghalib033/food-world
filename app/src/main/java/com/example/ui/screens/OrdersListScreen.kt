package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Moped
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Order
import com.example.data.model.OrderStatus
import com.example.ui.FoodDeliveryViewModel
import com.example.ui.Screen
import com.example.ui.components.CancelOrderDialog
import com.example.ui.components.VegNonVegBadge
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
fun OrdersListScreen(
    viewModel: FoodDeliveryViewModel,
    onBack: () -> Unit,
    listState: androidx.compose.foundation.lazy.LazyListState = androidx.compose.foundation.lazy.rememberLazyListState(),
    modifier: Modifier = Modifier
) {
    val orders by viewModel.orders.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmBg)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Your Orders",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Food World • Order History",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
        )

        if (orders.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.ReceiptLong,
                        contentDescription = null,
                        tint = RedPrimary,
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("No Orders Yet", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Your placed orders will show up here.", fontSize = 13.sp, color = TextSecondary)
                }
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(orders, key = { it.id }) { order ->
                    OrderHistoryCard(
                        order = order,
                        onTrack = { viewModel.navigateTo(Screen.OrderTracking(order.id)) },
                        onDetails = { viewModel.navigateTo(Screen.OrderDetail(order.id)) },
                        onCancel = { viewModel.cancelOrder(order.id) },
                        onHelp = { viewModel.navigateTo(Screen.HelpSupport(order.id)) }
                    )
                }
            }
        }
    }
}

@Composable
fun OrderHistoryCard(
    order: Order,
    onTrack: () -> Unit,
    onDetails: () -> Unit,
    onCancel: () -> Unit,
    onHelp: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDetails() }
            .testTag("order_card_${order.id.replace("#", "")}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header with Order ID, Price and 3-dot Menu
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Order ${order.id}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = order.orderTime,
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "₹${String.format("%.2f", order.totalAmount)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = RedPrimary
                    )

                    Box {
                        IconButton(
                            onClick = { menuExpanded = true },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Order Options")
                        }

                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Track Order") },
                                onClick = {
                                    menuExpanded = false
                                    onTrack()
                                },
                                leadingIcon = { Icon(Icons.Default.Moped, contentDescription = null, tint = RedPrimary) }
                            )

                            DropdownMenuItem(
                                text = { Text("View Details") },
                                onClick = {
                                    menuExpanded = false
                                    onDetails()
                                },
                                leadingIcon = { Icon(Icons.Default.Info, contentDescription = null, tint = TextPrimary) }
                            )

                            if (order.orderStatus.canBeCancelled()) {
                                DropdownMenuItem(
                                    text = { Text("Cancel Order", color = NonVegRed) },
                                    onClick = {
                                        menuExpanded = false
                                        onCancel()
                                    },
                                    leadingIcon = { Icon(Icons.Default.Cancel, contentDescription = null, tint = NonVegRed) }
                                )
                            }

                            DropdownMenuItem(
                                text = { Text("Help & Support") },
                                onClick = {
                                    menuExpanded = false
                                    onHelp()
                                },
                                leadingIcon = { Icon(Icons.AutoMirrored.Filled.Help, contentDescription = null, tint = TextSecondary) }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Payment and Status Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Payment: ${order.paymentMethod} (${order.paymentStatus})",
                    fontSize = 12.sp,
                    color = TextSecondary
                )

                // Status Badge pill
                val (statusBg, statusFg) = when (order.orderStatus) {
                    OrderStatus.DELIVERED -> Pair(Color(0xFFE8F5E9), StatusDelivered)
                    OrderStatus.CANCELLED -> Pair(Color(0xFFFFEBEE), NonVegRed)
                    OrderStatus.OUT_FOR_DELIVERY -> Pair(Color(0xFFEDE7F6), StatusOutForDelivery)
                    OrderStatus.COOKING -> Pair(Color(0xFFFFF3E0), StatusCooking)
                    OrderStatus.CONFIRMED -> Pair(Color(0xFFE3F2FD), VegGreen)
                }

                Box(
                    modifier = Modifier
                        .background(statusBg, RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = order.orderStatus.displayName,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusFg
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Ordered items preview
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF9FAFB), RoundedCornerShape(8.dp))
                    .padding(10.dp)
            ) {
                Text("Items:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextSecondary)
                order.items.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            VegNonVegBadge(isVeg = item.foodItem.isVeg)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${item.foodItem.name} × ${item.quantity}",
                                fontSize = 12.sp,
                                color = TextPrimary
                            )
                        }
                        Text(
                            text = "₹${String.format("%.2f", item.totalPrice)}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Quick Links
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onTrack,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Moped, contentDescription = null, modifier = Modifier.size(14.dp), tint = RedPrimary)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Track", fontSize = 12.sp, color = RedPrimary)
                }

                OutlinedButton(
                    onClick = onDetails,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Details", fontSize = 12.sp, color = TextPrimary)
                }
            }
        }
    }
}
