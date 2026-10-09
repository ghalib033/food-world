package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.CartItem
import com.example.data.model.FoodItem
import com.example.ui.FoodDeliveryViewModel
import com.example.ui.Screen
import com.example.ui.components.LiveTimeHeader
import com.example.ui.components.VegNonVegBadge
import com.example.ui.theme.AmberStar
import com.example.ui.theme.NonVegRed
import com.example.ui.theme.RedDark
import com.example.ui.theme.RedGradientEnd
import com.example.ui.theme.RedGradientStart
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VegGreen
import com.example.ui.theme.WarmBg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: FoodDeliveryViewModel,
    onOpenDrawer: () -> Unit,
    listState: androidx.compose.foundation.lazy.LazyListState = androidx.compose.foundation.lazy.rememberLazyListState(),
    modifier: Modifier = Modifier
) {
    val user by viewModel.currentUser.collectAsState()
    val filteredFoods by viewModel.filteredFoodItems.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val vegFilter by viewModel.vegFilter.collectAsState()
    val liveTime by viewModel.currentTimeString.collectAsState()
    val selectedLocation by viewModel.selectedLocation.collectAsState()

    val totalCartCount = cartItems.sumOf { it.quantity }
    val displayCity = selectedLocation?.first ?: user?.city ?: "Vadodara"
    val displayState = selectedLocation?.second ?: user?.state ?: "Gujarat"

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmBg)
    ) {
        // Top App Bar
        CenterAlignedTopAppBar(
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Food World",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = RedPrimary
                    )
                    Text(
                        text = "NonVegAi • Fresh Chicken & Veg Food",
                        fontSize = 10.sp,
                        color = TextSecondary
                    )
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = onOpenDrawer,
                    modifier = Modifier.testTag("menu_drawer_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Open Drawer",
                        tint = RedPrimary
                    )
                }
            },
            actions = {
                BadgedBox(
                    badge = {
                        if (totalCartCount > 0) {
                            Badge(
                                containerColor = RedPrimary,
                                contentColor = Color.White
                            ) {
                                Text("$totalCartCount")
                            }
                        }
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    IconButton(
                        onClick = { viewModel.navigateTo(Screen.Cart) },
                        modifier = Modifier.testTag("cart_icon_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            tint = RedPrimary
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.White
            )
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            // Live Date & Time + Location Header
            item {
                LiveTimeHeader(
                    userName = user?.fullName ?: "Friend",
                    city = displayCity,
                    state = displayState,
                    liveTimeString = liveTime,
                    onLocationClick = { viewModel.isLocationDialogVisible.value = true }
                )
            }

            // Promotional Hero Banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.linearGradient(listOf(Color(0xFF212121), Color(0xFF424242)))
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.banner_food_hero),
                        contentDescription = "Food Special Offer",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                                )
                            )
                            .padding(14.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .background(RedPrimary, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "CASH ON DELIVERY",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "• Free Delivery Over ₹200",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Text(
                                text = "Craving Royal Biryani & Fresh Parathas?",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Search Bar
            item {
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.searchQuery.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("food_search_input"),
                        placeholder = { Text("Search biryani, paratha, curry...", fontSize = 14.sp) },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Search", tint = RedPrimary)
                        },
                        trailingIcon = {
                            if (searchQuery.isNotBlank()) {
                                IconButton(onClick = { viewModel.searchQuery.value = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear")
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = RedPrimary,
                            unfocusedBorderColor = Color(0xFFE5E7EB)
                        )
                    )
                }
            }

            // Dietary Filters (All, Veg, Non-Veg)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterPill(
                        label = "All Food",
                        isSelected = vegFilter == null,
                        onClick = { viewModel.vegFilter.value = null }
                    )
                    FilterPill(
                        label = "Veg Only 🟢",
                        isSelected = vegFilter == true,
                        selectedColor = VegGreen,
                        onClick = { viewModel.vegFilter.value = if (vegFilter == true) null else true }
                    )
                    FilterPill(
                        label = "Non-Veg 🔴",
                        isSelected = vegFilter == false,
                        selectedColor = NonVegRed,
                        onClick = { viewModel.vegFilter.value = if (vegFilter == false) null else false }
                    )
                }
            }

            // Category Chips Row
            item {
                val categories = listOf(
                    "All",
                    "Biryani",
                    "Coffee & Beverages",
                    "Veg Thali",
                    "Curries",
                    "Starters",
                    "Breads & Parathas",
                    "Chinese & Noodles",
                    "Pizza & Fast Food",
                    "South Indian",
                    "Snacks & Street Food",
                    "Burgers & Sandwiches",
                    "Desserts"
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { cat ->
                        val isSelected = selectedCategory == cat
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (isSelected) RedPrimary else Color.White)
                                .border(
                                    1.dp,
                                    if (isSelected) RedPrimary else Color(0xFFE5E7EB),
                                    RoundedCornerShape(20.dp)
                                )
                                .clickable { viewModel.selectedCategory.value = cat }
                                .padding(horizontal = 14.dp, vertical = 7.dp)
                        ) {
                            Text(
                                text = cat,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else TextPrimary
                            )
                        }
                    }
                }
            }

            // Food Items Section Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Available in $displayCity",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "${filteredFoods.size} items",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }

            // Empty state if nothing matches
            if (filteredFoods.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("No food items found matching your filters in $displayCity.", color = TextSecondary)
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedButton(
                                onClick = {
                                    viewModel.searchQuery.value = ""
                                    viewModel.selectedCategory.value = "All"
                                    viewModel.vegFilter.value = null
                                    viewModel.clearLocation()
                                }
                            ) {
                                Text("Reset Filters / View All", color = RedPrimary)
                            }
                        }
                    }
                }
            }

            // Food Cards List
            items(filteredFoods, key = { it.id }) { food ->
                val inCart = cartItems.find { it.foodItem.id == food.id }
                FoodItemCard(
                    food = food,
                    cartItem = inCart,
                    onItemClick = { viewModel.navigateTo(Screen.ProductDetail(food.id)) },
                    onAddToCart = { viewModel.addToCart(food) },
                    onUpdateQuantity = { qty -> viewModel.updateCartQuantity(food.id, qty) },
                    onQuickOrder = { viewModel.quickOrder(food) }
                )
            }
        }
    }
}

@Composable
fun FilterPill(
    label: String,
    isSelected: Boolean,
    selectedColor: Color = RedPrimary,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) selectedColor else Color.White)
            .border(
                1.dp,
                if (isSelected) selectedColor else Color(0xFFE5E7EB),
                RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else TextPrimary
        )
    }
}

@Composable
fun FoodItemCard(
    food: FoodItem,
    cartItem: CartItem?,
    onItemClick: () -> Unit,
    onAddToCart: () -> Unit,
    onUpdateQuantity: (Int) -> Unit,
    onQuickOrder: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onItemClick() }
            .testTag("food_card_${food.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Food Image
                Box(
                    modifier = Modifier
                        .size(105.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    val mainImg = food.imageUrls.firstOrNull()
                    if (mainImg != null) {
                        AsyncImage(
                            model = mainImg,
                            contentDescription = food.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = food.imageResId),
                            error = painterResource(id = food.imageResId)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = food.imageResId),
                            contentDescription = food.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    // Veg / Non-Veg badge on image corner
                    Box(modifier = Modifier.padding(6.dp)) {
                        VegNonVegBadge(isVeg = food.isVeg)
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Info Column
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(105.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = food.name,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Text(
                            text = "${food.vendorName} • ${food.vendorCity}",
                            fontSize = 11.sp,
                            color = TextSecondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = food.description,
                            fontSize = 11.sp,
                            color = Color(0xFF6B7280),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 14.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Price in bold red (matching screenshot Fig 9.1: ₹99.99)
                        Text(
                            text = "₹${String.format("%.2f", food.price)}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = RedPrimary
                        )

                        // Rating badge
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(Color(0xFFFFF8E1), RoundedCornerShape(6.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = AmberStar,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "${food.rating}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF8D6E63)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action Buttons: Quick Order + Add to Cart
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Quick Order Button (1-tap COD directly from card!)
                OutlinedButton(
                    onClick = onQuickOrder,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = RedPrimary),
                    border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.linearGradient(listOf(RedPrimary, RedGradientEnd))),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp),
                    modifier = Modifier
                        .height(36.dp)
                        .testTag("quick_order_button_${food.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.FlashOn,
                        contentDescription = "Quick Order",
                        tint = RedPrimary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Order Now", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                // Add to Cart / Quantity controls
                if (cartItem == null) {
                    Button(
                        onClick = onAddToCart,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp)
                            .testTag("add_to_cart_button_${food.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add to Cart", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp)
                            .background(Color(0xFFFFEBEE), RoundedCornerShape(8.dp))
                            .border(1.dp, RedPrimary, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { onUpdateQuantity(cartItem.quantity - 1) },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = RedPrimary)
                        }

                        Text(
                            text = "${cartItem.quantity}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = RedPrimary
                        )

                        IconButton(
                            onClick = { onUpdateQuantity(cartItem.quantity + 1) },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Increase", tint = RedPrimary)
                        }
                    }
                }
            }
        }
    }
}
