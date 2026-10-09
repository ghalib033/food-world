package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.Screen
import com.example.ui.theme.RedPrimary

enum class BottomNavTab(
    val title: String,
    val icon: ImageVector,
    val screen: Screen,
    val testTag: String
) {
    HOME("Home", Icons.Default.Home, Screen.Home, "bottom_tab_home"),
    CATEGORIES("Categories", Icons.Default.RestaurantMenu, Screen.Categories, "bottom_tab_categories"),
    ORDERS("Orders", Icons.Default.ReceiptLong, Screen.OrdersList, "bottom_tab_orders"),
    CART("Cart", Icons.Default.ShoppingCart, Screen.Cart, "bottom_tab_cart")
}

@Composable
fun FoodWorldBottomNavBar(
    currentScreen: Screen,
    cartItemCount: Int,
    isVisible: Boolean,
    onTabSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(durationMillis = 280)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(durationMillis = 280)
        ),
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 12.dp, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .border(
                    width = 1.dp,
                    color = Color(0xFFEEEEEE),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                ),
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            color = Color.White
        ) {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                BottomNavTab.entries.forEach { tab ->
                    val isSelected = when (tab) {
                        BottomNavTab.HOME -> currentScreen is Screen.Home
                        BottomNavTab.CATEGORIES -> currentScreen is Screen.Categories
                        BottomNavTab.ORDERS -> currentScreen is Screen.OrdersList
                        BottomNavTab.CART -> currentScreen is Screen.Cart
                    }

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { onTabSelected(tab.screen) },
                        icon = {
                            if (tab == BottomNavTab.CART && cartItemCount > 0) {
                                BadgedBox(
                                    badge = {
                                        Badge(
                                            containerColor = RedPrimary,
                                            contentColor = Color.White
                                        ) {
                                            Text(
                                                text = if (cartItemCount > 99) "99+" else "$cartItemCount",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = tab.icon,
                                        contentDescription = tab.title,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.title,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        },
                        label = {
                            Text(
                                text = tab.title,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = RedPrimary,
                            selectedTextColor = RedPrimary,
                            unselectedIconColor = Color(0xFF9E9E9E),
                            unselectedTextColor = Color(0xFF9E9E9E),
                            indicatorColor = Color(0xFFFFEBEE)
                        ),
                        modifier = Modifier.testTag(tab.testTag)
                    )
                }
            }
        }
    }
}
