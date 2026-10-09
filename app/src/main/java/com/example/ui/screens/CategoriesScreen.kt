package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Icecream
import androidx.compose.material.icons.filled.KebabDining
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalPizza
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.RamenDining
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.FoodDeliveryViewModel
import com.example.ui.Screen
import com.example.ui.theme.NonVegRed
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VegGreen
import com.example.ui.theme.WarmBg

data class CategoryMeta(
    val name: String,
    val icon: ImageVector,
    val description: String,
    val badge: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    viewModel: FoodDeliveryViewModel,
    listState: LazyListState,
    onOpenDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    val foods by viewModel.allFoodItems.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val vegFilter by viewModel.vegFilter.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    val totalCartCount = cartItems.sumOf { it.quantity }

    val categoryList = listOf(
        CategoryMeta("All", Icons.Default.Restaurant, "All delicacies", "Everything"),
        CategoryMeta("Biryani", Icons.Default.RamenDining, "Royal Dum & Chicken Biryani", "Special"),
        CategoryMeta("Coffee & Beverages", Icons.Default.LocalCafe, "Espresso, Latte & Cold Coffee", "Fresh Brew"),
        CategoryMeta("Veg Thali", Icons.Default.DinnerDining, "Complete authentic thali meals", "Wholesome"),
        CategoryMeta("Curries", Icons.Default.DinnerDining, "Rich gravy, paneer & chicken curries", "Mains"),
        CategoryMeta("Starters", Icons.Default.KebabDining, "Crispy Manchurian, Tikkas & Kebabs", "Appetizers"),
        CategoryMeta("Breads & Parathas", Icons.Default.BakeryDining, "Stuffed parathas, naan & rotis", "Fresh"),
        CategoryMeta("Chinese & Noodles", Icons.Default.RamenDining, "Hakka noodles & fried rice", "Wok"),
        CategoryMeta("Pizza & Fast Food", Icons.Default.LocalPizza, "Oven baked veg & chicken pizzas", "Cheesy"),
        CategoryMeta("South Indian", Icons.Default.BreakfastDining, "Crispy masala dosa with chutneys", "Traditional"),
        CategoryMeta("Snacks & Street Food", Icons.Default.Fastfood, "Chole bhature, sandwiches & rolls", "Street"),
        CategoryMeta("Burgers & Sandwiches", Icons.Default.LunchDining, "Crispy burgers & grilled toast", "Bites"),
        CategoryMeta("Desserts", Icons.Default.Icecream, "Traditional sweets & desserts", "Sweet")
    )

    val categoryFilteredFoods = foods.filter { item ->
        val matchesCategory = selectedCategory == "All" || item.category.equals(selectedCategory, ignoreCase = true)
        val matchesVeg = when (vegFilter) {
            true -> item.isVeg
            false -> !item.isVeg
            else -> true
        }
        val matchesSearch = searchQuery.isBlank() ||
                item.name.contains(searchQuery, ignoreCase = true) ||
                item.category.contains(searchQuery, ignoreCase = true)

        matchesCategory && matchesVeg && matchesSearch
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmBg)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Food Categories",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = RedPrimary
                    )
                    Text(
                        text = "Explore by Cuisine & Diet",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = onOpenDrawer) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = RedPrimary)
                }
            },
            actions = {
                BadgedBox(
                    badge = {
                        if (totalCartCount > 0) {
                            Badge(containerColor = RedPrimary, contentColor = Color.White) {
                                Text("$totalCartCount")
                            }
                        }
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    IconButton(onClick = { viewModel.navigateTo(Screen.Cart) }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = RedPrimary)
                    }
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            // Search Input
            item {
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.searchQuery.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("category_search_input"),
                        placeholder = { Text("Search dishes in categories...", fontSize = 14.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = RedPrimary) },
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

            // Diet Pill Filter Row (All, Veg, Non-Veg)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterPill(
                        label = "All Diets",
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

            // Horizontal Category Selector Cards
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    categoryList.forEach { cat ->
                        val isSelected = selectedCategory == cat.name
                        Card(
                            modifier = Modifier
                                .width(130.dp)
                                .clickable { viewModel.selectedCategory.value = cat.name }
                                .testTag("cat_card_${cat.name.lowercase().replace(" ", "_")}"),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) Color(0xFFFFEBEE) else Color.White
                            ),
                            border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, RedPrimary) else null,
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .background(
                                            if (isSelected) RedPrimary else Color(0xFFF3F4F6),
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = cat.icon,
                                        contentDescription = cat.name,
                                        tint = if (isSelected) Color.White else RedPrimary,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = cat.name,
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                                    color = if (isSelected) RedPrimary else TextPrimary,
                                    maxLines = 1
                                )
                                Text(
                                    text = cat.badge,
                                    fontSize = 10.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }
            }

            // Header for filtered items
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (selectedCategory == "All") "All Dishes" else "$selectedCategory Dishes",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "${categoryFilteredFoods.size} items",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }

            // Dishes in the selected category
            items(categoryFilteredFoods, key = { it.id }) { food ->
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
