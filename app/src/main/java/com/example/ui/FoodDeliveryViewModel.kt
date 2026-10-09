package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.CartItem
import com.example.data.model.FoodItem
import com.example.data.model.Order
import com.example.data.model.OrderStatus
import com.example.data.model.SupportTicket
import com.example.data.model.UserProfile
import com.example.data.repository.FoodDeliveryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed interface Screen {
    data object Home : Screen
    data object Categories : Screen
    data class ProductDetail(val foodId: Long) : Screen
    data object Cart : Screen
    data class Checkout(val quickFoodId: Long? = null, val initialQty: Int = 1) : Screen
    data class OrderConfirmation(val orderId: String) : Screen
    data class OrderTracking(val orderId: String) : Screen
    data object OrdersList : Screen
    data class OrderDetail(val orderId: String) : Screen
    data class HelpSupport(val orderId: String) : Screen
    data object Profile : Screen
    data object AdminPanel : Screen
    data object Login : Screen
    data object Register : Screen
}

class FoodDeliveryViewModel(
    val repository: FoodDeliveryRepository = FoodDeliveryRepository()
) : ViewModel() {

    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _screenStack = mutableListOf<Screen>(Screen.Home)

    val currentUser: StateFlow<UserProfile?> = repository.currentUser
    val cartItems: StateFlow<List<CartItem>> = repository.cartItems
    val orders: StateFlow<List<Order>> = repository.orders
    val supportTickets: StateFlow<List<SupportTicket>> = repository.supportTickets
    val selectedLocation: StateFlow<Pair<String, String>?> = repository.selectedLocation

    val allFoodItems: StateFlow<List<FoodItem>> = repository.foodItems

    val searchQuery = MutableStateFlow("")
    val selectedCategory = MutableStateFlow("All")
    val vegFilter = MutableStateFlow<Boolean?>(null) // null = all, true = veg, false = non-veg

    val isLocationDialogVisible = MutableStateFlow(false)

    // Live clock on homepage as specified across practicals
    private val _currentTimeString = MutableStateFlow("")
    val currentTimeString: StateFlow<String> = _currentTimeString.asStateFlow()

    private val _userMessage = MutableSharedFlow<String>()
    val userMessage: SharedFlow<String> = _userMessage.asSharedFlow()

    // Filtered food list respecting user location, search query, veg/non-veg and category
    val filteredFoodItems: StateFlow<List<FoodItem>> = combine(
        allFoodItems,
        searchQuery,
        selectedCategory,
        vegFilter,
        selectedLocation
    ) { items: List<FoodItem>, query: String, category: String, vegOnly: Boolean?, explicitLoc: Pair<String, String>? ->
        val activeCity = explicitLoc?.first ?: currentUser.value?.city
        val activeState = explicitLoc?.second ?: currentUser.value?.state

        items.filter { item ->
            // Location filter: matching city and state (case-insensitive) OR global (if no explicit state)
            val matchesLocation = if (activeCity.isNullOrBlank() && activeState.isNullOrBlank()) {
                true
            } else {
                item.vendorCity.equals(activeCity, ignoreCase = true) ||
                        item.vendorState.equals(activeState, ignoreCase = true) ||
                        item.vendorName.contains("Global", ignoreCase = true)
            }

            val matchesSearch = query.isBlank() ||
                    item.name.contains(query, ignoreCase = true) ||
                    item.category.contains(query, ignoreCase = true) ||
                    item.description.contains(query, ignoreCase = true)

            val matchesCategory = category == "All" || item.category.equals(category, ignoreCase = true)

            val matchesVeg = when (vegOnly) {
                true -> item.isVeg
                false -> !item.isVeg
                else -> true
            }

            matchesLocation && matchesSearch && matchesCategory && matchesVeg
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        startLiveClock()
    }

    private fun startLiveClock() {
        viewModelScope.launch {
            val formatter = SimpleDateFormat("EEE, dd MMM yyyy • hh:mm:ss a", Locale.getDefault())
            while (true) {
                _currentTimeString.value = formatter.format(Date())
                delay(1000)
            }
        }
    }

    fun navigateTo(screen: Screen) {
        if (_currentScreen.value != screen) {
            _screenStack.add(screen)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (_screenStack.size > 1) {
            _screenStack.removeAt(_screenStack.size - 1)
            _currentScreen.value = _screenStack.last()
            return true
        }
        return false
    }

    fun addToCart(foodItem: FoodItem, qty: Int = 1) {
        repository.addToCart(foodItem, qty)
        viewModelScope.launch {
            _userMessage.emit("Added ${foodItem.name} to Cart")
        }
    }

    fun updateCartQuantity(foodItemId: Long, newQuantity: Int) {
        repository.updateCartQuantity(foodItemId, newQuantity)
    }

    fun removeFromCart(foodItemId: Long) {
        repository.removeFromCart(foodItemId)
    }

    fun placeCartOrder(address: String? = null, city: String? = null, state: String? = null) {
        // Clicking "Proceed to Checkout" opens Checkout page without placing immediately
        navigateTo(Screen.Checkout())
    }

    fun quickOrder(foodItem: FoodItem, quantity: Int = 1) {
        // Clicking "Order Now" opens Checkout page without placing immediately
        navigateTo(Screen.Checkout(quickFoodId = foodItem.id, initialQty = quantity))
    }

    fun confirmCheckoutOrder(
        items: List<CartItem>,
        customerName: String,
        customerMobile: String,
        address: String,
        city: String,
        state: String,
        idempotencyKey: String,
        onSuccess: (Order) -> Unit
    ) {
        try {
            val order = repository.placeOrder(
                itemsToOrder = items,
                customerName = customerName,
                customerMobile = customerMobile,
                customAddress = address,
                customCity = city,
                customState = state,
                idempotencyKey = idempotencyKey
            )
            onSuccess(order)
            navigateTo(Screen.OrderConfirmation(order.id))
        } catch (e: Exception) {
            viewModelScope.launch {
                _userMessage.emit(e.message ?: "Failed to place order")
            }
        }
    }

    fun cancelOrder(orderId: String, reason: String = "Ordered by mistake", onComplete: ((Boolean) -> Unit)? = null) {
        val result = repository.cancelOrder(orderId, reason)
        viewModelScope.launch {
            if (result.isSuccess) {
                _userMessage.emit("Your order has been cancelled successfully.")
                onComplete?.invoke(true)
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Cannot cancel order"
                _userMessage.emit(errorMsg)
                onComplete?.invoke(false)
            }
        }
    }

    fun updateOrderStatus(orderId: String, status: OrderStatus) {
        repository.updateOrderStatus(orderId, status)
        viewModelScope.launch {
            _userMessage.emit("Order $orderId updated to ${status.displayName}")
        }
    }

    fun toggleFoodAvailability(foodId: Long) {
        repository.toggleFoodAvailability(foodId)
    }

    fun updateFoodItem(item: FoodItem) {
        repository.updateFoodItem(item)
        viewModelScope.launch {
            _userMessage.emit("${item.name} updated successfully")
        }
    }

    fun submitSupportTicket(orderId: String, issueType: String, desc: String, email: String) {
        if (desc.isBlank()) {
            viewModelScope.launch { _userMessage.emit("Please enter issue description") }
            return
        }
        val ticket = repository.submitSupportTicket(orderId, issueType, desc, email)
        viewModelScope.launch {
            _userMessage.emit("Support ticket #${ticket.id} submitted. Our team will contact you!")
        }
        navigateBack()
    }

    fun setLocation(city: String, state: String) {
        repository.setLocationFilter(city, state)
        isLocationDialogVisible.value = false
        viewModelScope.launch {
            _userMessage.emit("Location updated to $city, $state")
        }
    }

    fun clearLocation() {
        repository.clearLocationFilter()
        isLocationDialogVisible.value = false
    }

    fun login(identifier: String, pass: String): Boolean {
        val success = repository.login(identifier, pass)
        if (success) {
            _currentScreen.value = Screen.Home
            _screenStack.clear()
            _screenStack.add(Screen.Home)
            viewModelScope.launch { _userMessage.emit("Welcome back!") }
        } else {
            viewModelScope.launch { _userMessage.emit("Invalid credentials (min 4 chars)") }
        }
        return success
    }

    fun register(name: String, mobile: String, email: String, address: String, city: String, state: String) {
        repository.register(name, mobile, email, address, city, state)
        _currentScreen.value = Screen.Home
        _screenStack.clear()
        _screenStack.add(Screen.Home)
        viewModelScope.launch { _userMessage.emit("Account created! Welcome $name") }
    }

    fun logout() {
        repository.logout()
        _currentScreen.value = Screen.Login
        _screenStack.clear()
        _screenStack.add(Screen.Login)
    }
}
