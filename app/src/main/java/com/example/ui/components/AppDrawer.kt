package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserProfile
import com.example.ui.Screen
import com.example.ui.theme.RedGradientEnd
import com.example.ui.theme.RedGradientStart
import com.example.ui.theme.RedPrimary
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun AppDrawerContent(
    user: UserProfile?,
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    onLogout: () -> Unit,
    onCloseDrawer: () -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier.width(300.dp),
        drawerContainerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            // Drawer Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            listOf(RedGradientStart, RedGradientEnd)
                        )
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Restaurant,
                            contentDescription = "Food World",
                            tint = RedPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Food World",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "NonVegAi – Chicken & Biryani",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = user?.fullName ?: "Guest User",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = user?.mobile ?: "No Mobile",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.75f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Navigation items
            DrawerMenuItem(
                icon = Icons.Default.Home,
                title = "Home & Menu",
                isSelected = currentScreen is Screen.Home,
                onClick = {
                    onNavigate(Screen.Home)
                    onCloseDrawer()
                }
            )

            DrawerMenuItem(
                icon = Icons.Default.RestaurantMenu,
                title = "Categories",
                isSelected = currentScreen is Screen.Categories,
                onClick = {
                    onNavigate(Screen.Categories)
                    onCloseDrawer()
                }
            )

            DrawerMenuItem(
                icon = Icons.Default.ReceiptLong,
                title = "My Orders",
                isSelected = currentScreen is Screen.OrdersList,
                onClick = {
                    onNavigate(Screen.OrdersList)
                    onCloseDrawer()
                }
            )

            DrawerMenuItem(
                icon = Icons.Default.Person,
                title = "My Profile",
                isSelected = currentScreen is Screen.Profile,
                onClick = {
                    onNavigate(Screen.Profile)
                    onCloseDrawer()
                }
            )

            DrawerMenuItem(
                icon = Icons.Default.LocationOn,
                title = "Delivery Address",
                isSelected = false,
                onClick = {
                    onNavigate(Screen.Profile)
                    onCloseDrawer()
                }
            )

            DrawerMenuItem(
                icon = Icons.Default.Payments,
                title = "Payment Methods (COD)",
                isSelected = false,
                onClick = {
                    onNavigate(Screen.Cart)
                    onCloseDrawer()
                }
            )

            DrawerMenuItem(
                icon = Icons.AutoMirrored.Filled.Help,
                title = "Help & Support",
                isSelected = currentScreen is Screen.HelpSupport,
                onClick = {
                    onNavigate(Screen.HelpSupport("#94"))
                    onCloseDrawer()
                }
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color(0xFFEEEEEE)
            )

            DrawerMenuItem(
                icon = Icons.Default.AdminPanelSettings,
                title = "Admin & Testing Lab",
                isSelected = currentScreen is Screen.AdminPanel,
                onClick = {
                    onNavigate(Screen.AdminPanel)
                    onCloseDrawer()
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color(0xFFEEEEEE)
            )

            DrawerMenuItem(
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                title = "Logout",
                isSelected = false,
                textColor = RedPrimary,
                onClick = {
                    onLogout()
                    onCloseDrawer()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun DrawerMenuItem(
    icon: ImageVector,
    title: String,
    isSelected: Boolean,
    textColor: Color = TextPrimary,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .background(
                if (isSelected) Color(0xFFFFEBEE) else Color.Transparent,
                RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 12.dp)
            .testTag("drawer_item_${title.lowercase().replace(" ", "_")}"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = if (isSelected) RedPrimary else textColor,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) RedPrimary else textColor
        )
    }
}
