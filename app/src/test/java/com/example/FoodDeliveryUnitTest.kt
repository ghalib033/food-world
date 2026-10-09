package com.example

import com.example.data.model.CartItem
import com.example.data.model.FoodItem
import com.example.data.model.OrderStatus
import com.example.data.repository.FoodDeliveryRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FoodDeliveryUnitTest {

    private lateinit var repository: FoodDeliveryRepository

    @Before
    fun setUp() {
        repository = FoodDeliveryRepository()
    }

    @Test
    fun test_cod_order_saves_without_transaction_id() {
        val foodItem = repository.foodItems.value.first()
        val cartItems = listOf(CartItem(foodItem, 1))

        val order = repository.placeOrder(
            itemsToOrder = cartItems,
            customAddress = "Pipe Line Road, Tandalja",
            customCity = "Vadodara",
            customState = "Gujarat"
        )

        // Verifies COD specification from Practical 8
        assertNull(order.transactionId)
        assertEquals("Cash on Delivery", order.paymentMethod)
        assertEquals("Pending", order.paymentStatus)
        assertEquals(OrderStatus.CONFIRMED, order.orderStatus)
    }

    @Test
    fun test_cancel_order_blocks_delivered_orders() {
        // Find existing delivered order (#90)
        val deliveredOrder = repository.orders.value.first { it.orderStatus == OrderStatus.DELIVERED }
        val result = repository.cancelOrder(deliveredOrder.id)

        // Must refuse to cancel an already-Delivered order per Practical 8 & 9 guardrails
        assertTrue(result.isFailure)
        assertEquals(OrderStatus.DELIVERED, repository.orders.value.first { it.id == deliveredOrder.id }.orderStatus)
    }

    @Test
    fun test_cancel_order_succeeds_for_confirmed_or_cooking_order() {
        val confirmedOrder = repository.orders.value.first { it.orderStatus == OrderStatus.CONFIRMED }
        val result = repository.cancelOrder(confirmedOrder.id)

        assertTrue(result.isSuccess)
        assertEquals(OrderStatus.CANCELLED, repository.orders.value.first { it.id == confirmedOrder.id }.orderStatus)
    }

    @Test
    fun test_cart_management_and_total_calculation() {
        val item1 = repository.foodItems.value[0]
        val item2 = repository.foodItems.value[1]

        repository.clearCart()
        repository.addToCart(item1, 2)
        assertEquals(1, repository.cartItems.value.size)
        assertEquals(2, repository.cartItems.value[0].quantity)

        repository.addToCart(item2, 1)
        assertEquals(2, repository.cartItems.value.size)

        // Total check
        val expectedTotal = (item1.price * 2) + (item2.price * 1)
        val actualTotal = repository.cartItems.value.sumOf { it.totalPrice }
        assertEquals(expectedTotal, actualTotal, 0.01)

        // Remove item
        repository.removeFromCart(item1.id)
        assertEquals(1, repository.cartItems.value.size)
        assertEquals(item2.id, repository.cartItems.value[0].foodItem.id)
    }

    @Test
    fun test_support_ticket_submission() {
        val ticket = repository.submitSupportTicket(
            orderId = "#94",
            issueType = "Order Delay",
            description = "Food delivery delayed by 20 minutes",
            email = "customer@example.com"
        )

        assertNotNull(ticket.id)
        assertEquals("#94", ticket.orderId)
        assertEquals("Order Delay", ticket.issueType)
        assertTrue(repository.supportTickets.value.any { it.id == ticket.id })
    }
}
