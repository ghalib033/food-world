package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Store
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.OrderStatus
import com.example.ui.FoodDeliveryViewModel
import com.example.ui.Screen
import com.example.ui.components.VegNonVegBadge
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.StatusCooking
import com.example.ui.theme.StatusOutForDelivery
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VegGreen
import com.example.ui.theme.WarmBg
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.FoodItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    viewModel: FoodDeliveryViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val orders by viewModel.orders.collectAsState()
    val foods by viewModel.allFoodItems.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }
    var editingFood by remember { mutableStateOf<FoodItem?>(null) }

    val totalRevenue = orders.filter { it.orderStatus != OrderStatus.CANCELLED }.sumOf { it.totalAmount }
    val activeOrdersCount = orders.count { it.orderStatus == OrderStatus.CONFIRMED || it.orderStatus == OrderStatus.COOKING || it.orderStatus == OrderStatus.OUT_FOR_DELIVERY }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmBg)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Admin & Vendor Console",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Food World • Django Admin Replica",
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

        // Metrics Summary Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Total COD Revenue", fontSize = 11.sp, color = TextSecondary)
                    Text("₹${String.format("%.2f", totalRevenue)}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = RedPrimary)
                    Text("${orders.size} orders recorded", fontSize = 10.sp, color = VegGreen)
                }
            }

            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Active Deliveries", fontSize = 11.sp, color = TextSecondary)
                    Text("$activeOrdersCount in transit", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
                    Text("Kitchen & Rider active", fontSize = 10.sp, color = TextSecondary)
                }
            }
        }

        // Tabs: Manage Orders vs Manage Menu Items
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Orders Control (${orders.size})", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Menu Inventory (${foods.size})", fontWeight = FontWeight.Bold) }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (selectedTab == 0) {
            // Orders Control List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(orders, key = { it.id }) { order ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("Order ${order.id}", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
                                    Text(order.orderTime, fontSize = 11.sp, color = TextSecondary)
                                }
                                Text("₹${String.format("%.2f", order.totalAmount)}", fontWeight = FontWeight.Black, fontSize = 15.sp, color = RedPrimary)
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Address: ${order.deliveryAddress}, ${order.deliveryCity}", fontSize = 12.sp, color = TextSecondary)

                            Spacer(modifier = Modifier.height(10.dp))

                            Text("Update Live Status:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                val statusOptions = listOf(
                                    OrderStatus.CONFIRMED,
                                    OrderStatus.COOKING,
                                    OrderStatus.OUT_FOR_DELIVERY,
                                    OrderStatus.DELIVERED
                                )

                                statusOptions.forEach { opt ->
                                    val isCurrent = order.orderStatus == opt
                                    Button(
                                        onClick = { viewModel.updateOrderStatus(order.id, opt) },
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isCurrent) RedPrimary else Color(0xFFF3F4F6),
                                            contentColor = if (isCurrent) Color.White else TextPrimary
                                        ),
                                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(34.dp)
                                    ) {
                                        Text(opt.displayName.take(7), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Menu Inventory List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(foods, key = { it.id }) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Product Main Image Thumbnail
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                ) {
                                    val mainImg = item.imageUrls.firstOrNull()
                                    if (mainImg != null) {
                                        AsyncImage(
                                            model = mainImg,
                                            contentDescription = item.name,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop,
                                            placeholder = painterResource(id = item.imageResId),
                                            error = painterResource(id = item.imageResId)
                                        )
                                    } else {
                                        Image(
                                            painter = painterResource(id = item.imageResId),
                                            contentDescription = item.name,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        VegNonVegBadge(isVeg = item.isVeg)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(item.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
                                    }
                                    Text("${item.category} • ${item.vendorName}", fontSize = 11.sp, color = TextSecondary)
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text("₹${String.format("%.2f", item.price)}", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = RedPrimary)
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.PhotoLibrary, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(12.dp))
                                            Spacer(modifier = Modifier.width(2.dp))
                                            Text("${item.imageUrls.size.coerceAtLeast(1)} images", fontSize = 10.sp, color = TextSecondary)
                                        }
                                    }
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = if (item.available) "In Stock" else "Out of Stock",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (item.available) VegGreen else Color.Gray
                                    )
                                    Switch(
                                        checked = item.available,
                                        onCheckedChange = { viewModel.toggleFoodAvailability(item.id) },
                                        colors = SwitchDefaults.colors(
                                            checkedThumbColor = Color.White,
                                            checkedTrackColor = VegGreen
                                        )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                OutlinedButton(
                                    onClick = { editingFood = item },
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(14.dp), tint = RedPrimary)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Edit 3 Images & Price", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = RedPrimary)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Edit Dish Dialog
        editingFood?.let { food ->
            EditProductDialog(
                food = food,
                onDismiss = { editingFood = null },
                onSave = { updated ->
                    viewModel.updateFoodItem(updated)
                    editingFood = null
                }
            )
        }
    }
}

@Composable
fun EditProductDialog(
    food: FoodItem,
    onDismiss: () -> Unit,
    onSave: (FoodItem) -> Unit
) {
    var priceText by remember { mutableStateOf(food.price.toString()) }
    var img1 by remember { mutableStateOf(food.imageUrls.getOrNull(0) ?: "") }
    var img2 by remember { mutableStateOf(food.imageUrls.getOrNull(1) ?: "") }
    var img3 by remember { mutableStateOf(food.imageUrls.getOrNull(2) ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    VegNonVegBadge(isVeg = food.isVeg)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Edit ${food.name}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Text("Manage 3 High-Quality Images & Pricing", fontSize = 11.sp, color = TextSecondary)
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Live preview of images
                Text("Current Image Previews:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf(img1, img2, img3).forEachIndexed { idx, url ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(55.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFF3F4F6))
                        ) {
                            if (url.isNotBlank()) {
                                AsyncImage(
                                    model = url,
                                    contentDescription = "Image ${idx + 1}",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop,
                                    placeholder = painterResource(id = food.imageResId),
                                    error = painterResource(id = food.imageResId)
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = food.imageResId),
                                    contentDescription = "Image ${idx + 1}",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    value = priceText,
                    onValueChange = { priceText = it },
                    label = { Text("Price (₹)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = img1,
                    onValueChange = { img1 = it },
                    label = { Text("Image 1 URL (Main card photo)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = img2,
                    onValueChange = { img2 = it },
                    label = { Text("Image 2 URL (Detail swipe photo)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = img3,
                    onValueChange = { img3 = it },
                    label = { Text("Image 3 URL (Detail swipe photo)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val newPrice = priceText.toDoubleOrNull() ?: food.price
                    val newUrls = listOf(img1, img2, img3).filter { it.isNotBlank() }
                    val updated = food.copy(
                        price = newPrice,
                        imageUrls = if (newUrls.isNotEmpty()) newUrls else food.imageUrls
                    )
                    onSave(updated)
                },
                colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
            ) {
                Text("Save Changes")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
