package com.example

import com.example.data.repository.FoodCatalog
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testFoodCatalog_hasExact35ItemsWith3ImagesEach() {
    val items = FoodCatalog.allProducts
    assertEquals(35, items.size)

    val vegItems = items.filter { it.isVeg && it.category != "Coffee & Beverages" }
    val coffeeItems = items.filter { it.category == "Coffee & Beverages" }
    val nonVegItems = items.filter { !it.isVeg }

    assertEquals(15, vegItems.size)
    assertEquals(15, nonVegItems.size)
    assertEquals(5, coffeeItems.size)

    // Verify every single item has exactly 3 images, non-empty description, and positive price
    for (item in items) {
      assertEquals("Item ${item.name} should have 3 images", 3, item.imageUrls.size)
      assertTrue("Item ${item.name} price should be > 0", item.price > 0)
      assertTrue("Item ${item.name} description should not be empty", item.description.isNotBlank())
      assertTrue("Item ${item.name} should be available", item.available)
    }
  }
}
