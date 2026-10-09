package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.ui.FoodDeliveryViewModel
import com.example.ui.Screen
import com.example.ui.components.AppDrawerContent
import com.example.ui.components.FoodWorldBottomNavBar
import com.example.ui.components.LocationPickerModal
import com.example.ui.screens.AdminDashboardScreen
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.CartScreen
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.HelpSupportScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OrderDetailScreen
import com.example.ui.screens.OrderConfirmScreen
import com.example.ui.screens.OrderTrackingScreen
import com.example.ui.screens.OrdersListScreen
import com.example.ui.screens.ProductDetailScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.theme.MyApplicationTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: FoodDeliveryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                FoodWorldApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun FoodWorldApp(viewModel: FoodDeliveryViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val isLocationDialogVisible by viewModel.isLocationDialogVisible.collectAsState()
    val selectedLocation by viewModel.selectedLocation.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Persistent scroll states for tabs so switching maintains exact scroll positions
    val homeListState = rememberLazyListState()
    val categoriesListState = rememberLazyListState()
    val ordersListState = rememberLazyListState()
    val cartListState = rememberLazyListState()

    // Hide on scroll state
    var isBottomBarVisible by remember { mutableStateOf(true) }

    // Always show bottom bar whenever switching tabs/screens
    LaunchedEffect(currentScreen) {
        isBottomBarVisible = true
    }

    // Determine active scroll state for top-of-page detection
    val activeListState = when (currentScreen) {
        is Screen.Home -> homeListState
        is Screen.Categories -> categoriesListState
        is Screen.OrdersList -> ordersListState
        is Screen.Cart -> cartListState
        else -> null
    }

    // Always restore the bottom navigation bar when user is at the very top of the list
    LaunchedEffect(activeListState) {
        if (activeListState != null) {
            snapshotFlow {
                activeListState.firstVisibleItemIndex == 0 && activeListState.firstVisibleItemScrollOffset <= 10
            }.collect { isAtTop ->
                if (isAtTop) {
                    isBottomBarVisible = true
                }
            }
        }
    }

    // NestedScrollConnection: slide down on scroll down, slide up on scroll up
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y < -12f) {
                    // User scrolling down -> hide bottom bar
                    isBottomBarVisible = false
                } else if (available.y > 12f) {
                    // User scrolling up -> show bottom bar
                    isBottomBarVisible = true
                }
                return Offset.Zero
            }
        }
    }

    val showBottomBar = currentScreen is Screen.Home ||
            currentScreen is Screen.Categories ||
            currentScreen is Screen.OrdersList ||
            currentScreen is Screen.Cart

    // Collect one-off messages (e.g. Added to Cart, order cancelled, etc.)
    LaunchedEffect(Unit) {
        viewModel.userMessage.collectLatest { msg ->
            snackbarHostState.showSnackbar(msg)
        }
    }

    // System BackHandler to pop the custom screen stack
    BackHandler(enabled = currentScreen !is Screen.Home && currentScreen !is Screen.Login) {
        if (!viewModel.navigateBack()) {
            viewModel.navigateTo(Screen.Home)
        }
    }

    // Location Picker Dialog Modal
    if (isLocationDialogVisible) {
        LocationPickerModal(
            currentCity = selectedLocation?.first ?: currentUser?.city ?: "Vadodara",
            currentState = selectedLocation?.second ?: currentUser?.state ?: "Gujarat",
            onDismiss = { viewModel.isLocationDialogVisible.value = false },
            onSaveLocation = { city, state -> viewModel.setLocation(city, state) },
            onClearFilter = { viewModel.clearLocation() }
        )
    }

    // If on Auth screens, no drawer needed
    if (currentScreen is Screen.Login || currentScreen is Screen.Register) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                AuthScreen(
                    viewModel = viewModel,
                    initialTab = if (currentScreen is Screen.Register) 1 else 0
                )
            }
        }
        return
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                user = currentUser,
                currentScreen = currentScreen,
                onNavigate = { screen ->
                    viewModel.navigateTo(screen)
                },
                onLogout = {
                    viewModel.logout()
                },
                onCloseDrawer = {
                    coroutineScope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .nestedScroll(nestedScrollConnection)
            ) {
                when (val screen = currentScreen) {
                    is Screen.Home -> {
                        HomeScreen(
                            viewModel = viewModel,
                            listState = homeListState,
                            onOpenDrawer = {
                                coroutineScope.launch { drawerState.open() }
                            }
                        )
                    }

                    is Screen.Categories -> {
                        CategoriesScreen(
                            viewModel = viewModel,
                            listState = categoriesListState,
                            onOpenDrawer = {
                                coroutineScope.launch { drawerState.open() }
                            }
                        )
                    }

                    is Screen.ProductDetail -> {
                        ProductDetailScreen(
                            foodId = screen.foodId,
                            viewModel = viewModel,
                            onBack = { viewModel.navigateBack() }
                        )
                    }

                    is Screen.Cart -> {
                        CartScreen(
                            viewModel = viewModel,
                            listState = cartListState,
                            onBack = { viewModel.navigateBack() }
                        )
                    }

                    is Screen.OrderConfirmation -> {
                        OrderConfirmScreen(
                            orderId = screen.orderId,
                            viewModel = viewModel
                        )
                    }

                    is Screen.OrderTracking -> {
                        OrderTrackingScreen(
                            orderId = screen.orderId,
                            viewModel = viewModel,
                            onBack = { viewModel.navigateBack() }
                        )
                    }

                    is Screen.OrdersList -> {
                        OrdersListScreen(
                            viewModel = viewModel,
                            listState = ordersListState,
                            onBack = { viewModel.navigateBack() }
                        )
                    }

                    is Screen.OrderDetail -> {
                        OrderDetailScreen(
                            orderId = screen.orderId,
                            viewModel = viewModel,
                            onBack = { viewModel.navigateBack() }
                        )
                    }

                    is Screen.HelpSupport -> {
                        HelpSupportScreen(
                            orderId = screen.orderId,
                            viewModel = viewModel,
                            onBack = { viewModel.navigateBack() }
                        )
                    }

                    is Screen.Profile -> {
                        ProfileScreen(
                            viewModel = viewModel,
                            onBack = { viewModel.navigateBack() }
                        )
                    }

                    is Screen.AdminPanel -> {
                        AdminDashboardScreen(
                            viewModel = viewModel,
                            onBack = { viewModel.navigateBack() }
                        )
                    }

                    is Screen.Login, is Screen.Register -> {
                        // Handled above
                    }
                }

                // Smooth animated slide-in / slide-out Bottom Navigation Bar
                if (showBottomBar) {
                    FoodWorldBottomNavBar(
                        currentScreen = currentScreen,
                        cartItemCount = cartItems.sumOf { it.quantity },
                        isVisible = isBottomBarVisible,
                        onTabSelected = { targetScreen ->
                            viewModel.navigateTo(targetScreen)
                        },
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }
}
