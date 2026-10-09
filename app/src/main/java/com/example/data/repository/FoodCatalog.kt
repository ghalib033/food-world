package com.example.data.repository

import com.example.R
import com.example.data.model.FoodItem

object FoodCatalog {

    val allProducts: List<FoodItem> = listOf(
        // ==================== VEG ITEMS (15 Items) ====================
        FoodItem(
            id = 101L,
            name = "Veg Biryani",
            category = "Biryani",
            isVeg = true,
            price = 149.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1633945274405-b6c8069047b0?w=800&auto=format&fit=crop"
            ),
            vendorName = "Royal Veg Biryani Darbar",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Fragrant long-grain basmati rice layered with seasonal vegetables, paneer cubes, saffron, caramelized onions, and fresh mint. Served with cold cucumber raita.",
            rating = 4.7f,
            prepTimeMinutes = 25
        ),
        FoodItem(
            id = 102L,
            name = "Paneer Butter Masala",
            category = "Curries",
            isVeg = true,
            price = 199.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1567184109191-3783b3218ce7?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1596797038530-2c107229654b?w=800&auto=format&fit=crop"
            ),
            vendorName = "Punjab Rasoi",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Soft malai paneer cubes simmered in a luscious tomato and cashew butter gravy, scented with kasuri methi and fresh double cream.",
            rating = 4.8f,
            prepTimeMinutes = 20
        ),
        FoodItem(
            id = 103L,
            name = "Shahi Paneer",
            category = "Curries",
            isVeg = true,
            price = 219.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1567184109191-3783b3218ce7?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=800&auto=format&fit=crop"
            ),
            vendorName = "Mughlai Heritage",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Rich royal curry featuring tender cottage cheese chunks in a velvety almond-cashew white gravy flavored with cardamom, saffron, and subtle spices.",
            rating = 4.9f,
            prepTimeMinutes = 25
        ),
        FoodItem(
            id = 104L,
            name = "Paneer Tikka",
            category = "Starters",
            isVeg = true,
            price = 189.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1567184109191-3783b3218ce7?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1599488615731-7e5c2823ff28?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=800&auto=format&fit=crop"
            ),
            vendorName = "Tandoor Nights",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Skewered cubes of fresh paneer, crunchy bell peppers, and red onions marinated in spiced mustard oil yogurt and chargrilled in a clay oven.",
            rating = 4.7f,
            prepTimeMinutes = 20
        ),
        FoodItem(
            id = 105L,
            name = "Dal Makhani",
            category = "Curries",
            isVeg = true,
            price = 159.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1567184109191-3783b3218ce7?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=800&auto=format&fit=crop"
            ),
            vendorName = "Desi Dhaba",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Whole black lentils and rajma slow-simmered for 12 hours with butter, fresh tomato puree, and cream for an authentic smoky Punjabi flavor.",
            rating = 4.8f,
            prepTimeMinutes = 15
        ),
        FoodItem(
            id = 106L,
            name = "Dal Tadka",
            category = "Curries",
            isVeg = true,
            price = 119.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=800&auto=format&fit=crop"
            ),
            vendorName = "Desi Dhaba",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Yellow toor dal tempered with sizzling desi ghee, cumin seeds, aromatic minced garlic, dried Kashmiri red chilies, and fresh green coriander.",
            rating = 4.6f,
            prepTimeMinutes = 15
        ),
        FoodItem(
            id = 107L,
            name = "Chole Bhature",
            category = "Snacks & Street Food",
            isVeg = true,
            price = 129.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=800&auto=format&fit=crop"
            ),
            vendorName = "Delhi Sweets & Snacks",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Spicy and tangy Punjabi chickpea masala cooked with dark anardana, served with two giant fluffy puffed bhaturas, pickled carrot, and sliced onions.",
            rating = 4.9f,
            prepTimeMinutes = 20
        ),
        FoodItem(
            id = 108L,
            name = "Aloo Paratha",
            category = "Breads & Parathas",
            isVeg = true,
            price = 79.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=800&auto=format&fit=crop"
            ),
            vendorName = "Desi Paratha Corner",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Whole wheat flatbread stuffed with spiced mashed potatoes, ginger, roasted cumin, and green chillies. Grilled on tawa with desi butter.",
            rating = 4.7f,
            prepTimeMinutes = 15
        ),
        FoodItem(
            id = 109L,
            name = "Butter Naan",
            category = "Breads & Parathas",
            isVeg = true,
            price = 39.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=800&auto=format&fit=crop"
            ),
            vendorName = "Tandoor Nights",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Soft, fluffy leavened bread slapped on the walls of a scorching clay tandoor, pulled fresh and slathered with pure melted dairy butter.",
            rating = 4.8f,
            prepTimeMinutes = 10
        ),
        FoodItem(
            id = 110L,
            name = "Paneer Tikka Masala",
            category = "Curries",
            isVeg = true,
            price = 209.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1567184109191-3783b3218ce7?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=800&auto=format&fit=crop"
            ),
            vendorName = "Punjab Rasoi",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Smoky char-grilled paneer tikka chunks simmered in a spiced, tangy onion-tomato masala with diced capsicum and roasted garam masala.",
            rating = 4.8f,
            prepTimeMinutes = 25
        ),
        FoodItem(
            id = 111L,
            name = "Veg Manchurian",
            category = "Starters",
            isVeg = true,
            price = 139.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1540420773420-3366772f4999?w=800&auto=format&fit=crop"
            ),
            vendorName = "Wok & Spice",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Deep-fried shredded vegetable balls tossed in a savory Indo-Chinese sauce of dark soy, chili paste, garlic, ginger, and crunchy scallions.",
            rating = 4.6f,
            prepTimeMinutes = 18
        ),
        FoodItem(
            id = 112L,
            name = "Veg Hakka Noodles",
            category = "Chinese & Noodles",
            isVeg = true,
            price = 129.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1612927601601-6638404737ce?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1585032226651-759b368d7246?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=800&auto=format&fit=crop"
            ),
            vendorName = "Wok & Spice",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Wok-tossed noodles with julienned cabbage, bell peppers, carrots, and spring onions, tossed in light soy sauce and white pepper on high heat.",
            rating = 4.7f,
            prepTimeMinutes = 20
        ),
        FoodItem(
            id = 113L,
            name = "Veg Pizza",
            category = "Pizza & Fast Food",
            isVeg = true,
            price = 229.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1534308983496-4fabb1a015ee?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=800&auto=format&fit=crop"
            ),
            vendorName = "Oven Fresh Pizzeria",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Hand-stretched crispy crust smothered with herb tomato concasse, 100% mozzarella, sweet corn, black olives, bell peppers, and mushrooms.",
            rating = 4.8f,
            prepTimeMinutes = 25
        ),
        FoodItem(
            id = 114L,
            name = "Masala Dosa",
            category = "South Indian",
            isVeg = true,
            price = 99.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1610192244261-3f33de3f55e4?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=800&auto=format&fit=crop"
            ),
            vendorName = "Udupi Cafe",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Crispy thin fermented rice-lentil crepe smeared with spicy red chutney, filled with mustard-tempered potato masala, served with coconut chutney & sambar.",
            rating = 4.9f,
            prepTimeMinutes = 15
        ),
        FoodItem(
            id = 115L,
            name = "Veg Grilled Sandwich",
            category = "Snacks & Street Food",
            isVeg = true,
            price = 89.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1528735602780-2552fd46c7af?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1553909489-cd47e0907980?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1509722747041-616f39b57569?w=800&auto=format&fit=crop"
            ),
            vendorName = "Bombay Sandwich Co.",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Three-tiered grilled bread packed with spiced potato slices, juicy tomatoes, cucumbers, capsicum, processed cheese, and spicy mint-coriander chutney.",
            rating = 4.6f,
            prepTimeMinutes = 12
        ),

        // ==================== NON-VEG ITEMS (15 Items) ====================
        FoodItem(
            id = 201L,
            name = "Chicken Biryani",
            category = "Biryani",
            isVeg = false,
            price = 199.00,
            imageResId = R.drawable.dish_biryani,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1633945274405-b6c8069047b0?w=800&auto=format&fit=crop"
            ),
            vendorName = "NonVegAi Biryani Darbar",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Fragrant long-grain basmati rice slow-cooked with spiced succulent chicken cuts, fried onions, mint, and pure desi ghee. Served with cold raita.",
            rating = 4.8f,
            prepTimeMinutes = 30
        ),
        FoodItem(
            id = 202L,
            name = "Chicken Dum Biryani",
            category = "Biryani",
            isVeg = false,
            price = 249.00,
            imageResId = R.drawable.dish_biryani,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1633945274405-b6c8069047b0?w=800&auto=format&fit=crop"
            ),
            vendorName = "NonVegAi Biryani Darbar",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Royal Hyderabadi dum biryani sealed with dough, allowing the flavors of saffron, tender chicken drumsticks, star anise, and mint to infuse deeply.",
            rating = 4.9f,
            prepTimeMinutes = 35
        ),
        FoodItem(
            id = 203L,
            name = "Chicken Tikka",
            category = "Starters",
            isVeg = false,
            price = 229.00,
            imageResId = R.drawable.dish_biryani,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1599488615731-7e5c2823ff28?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1610057099431-d73a1c9d2f2f?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=800&auto=format&fit=crop"
            ),
            vendorName = "Tandoor Nights",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Tender boneless chicken breast morsels steeped in red Kashmiri degi mirch, hung curd, mustard oil, and char-grilled over flaming lump charcoal.",
            rating = 4.8f,
            prepTimeMinutes = 22
        ),
        FoodItem(
            id = 204L,
            name = "Butter Chicken",
            category = "Curries",
            isVeg = false,
            price = 269.00,
            imageResId = R.drawable.dish_biryani,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1588166524941-3bf61a9c41db?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=800&auto=format&fit=crop"
            ),
            vendorName = "Pind Punjab",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "World-famous Murgh Makhani: shredded tandoori roasted chicken in a silky, mildly spiced buttery tomato-cashew reduction garnished with heavy cream.",
            rating = 4.9f,
            prepTimeMinutes = 25
        ),
        FoodItem(
            id = 205L,
            name = "Chicken Curry",
            category = "Curries",
            isVeg = false,
            price = 199.00,
            imageResId = R.drawable.dish_biryani,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1588166524941-3bf61a9c41db?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=800&auto=format&fit=crop"
            ),
            vendorName = "Desi Dhaba",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Homestyle aromatic chicken curry prepared with browned onions, ginger-garlic paste, fresh coriander, and traditional whole spices.",
            rating = 4.7f,
            prepTimeMinutes = 22
        ),
        FoodItem(
            id = 206L,
            name = "Kadai Chicken",
            category = "Curries",
            isVeg = false,
            price = 249.00,
            imageResId = R.drawable.dish_biryani,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1588166524941-3bf61a9c41db?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1599488615731-7e5c2823ff28?w=800&auto=format&fit=crop"
            ),
            vendorName = "Punjab Rasoi",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Juicy chicken pieces tossed in an iron kadai with crisp diced capsicum, onions, and freshly ground roasted coriander-peppercorn kadai masala.",
            rating = 4.8f,
            prepTimeMinutes = 25
        ),
        FoodItem(
            id = 207L,
            name = "Tandoori Chicken",
            category = "Starters",
            isVeg = false,
            price = 299.00,
            imageResId = R.drawable.dish_biryani,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1599488615731-7e5c2823ff28?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1610057099431-d73a1c9d2f2f?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=800&auto=format&fit=crop"
            ),
            vendorName = "Tandoor Nights",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Half chicken marinated overnight in hung curd, Kashmiri chilies, and royal tandoori spices, roasted in clay oven to juicy, smoky perfection.",
            rating = 4.9f,
            prepTimeMinutes = 30
        ),
        FoodItem(
            id = 208L,
            name = "Chicken Fried Rice",
            category = "Chinese & Noodles",
            isVeg = false,
            price = 159.00,
            imageResId = R.drawable.dish_biryani,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1612927601601-6638404737ce?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1585032226651-759b368d7246?w=800&auto=format&fit=crop"
            ),
            vendorName = "Wok & Spice",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Wok-fried long-grain rice tossed with seasoned chicken shreds, scrambled eggs, finely diced carrots, bell peppers, and scallions in light soy sauce.",
            rating = 4.6f,
            prepTimeMinutes = 18
        ),
        FoodItem(
            id = 209L,
            name = "Chicken Hakka Noodles",
            category = "Chinese & Noodles",
            isVeg = false,
            price = 159.00,
            imageResId = R.drawable.dish_biryani,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1612927601601-6638404737ce?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1585032226651-759b368d7246?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=800&auto=format&fit=crop"
            ),
            vendorName = "Wok & Spice",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "High-heat stir-fried noodles tossed with boneless chicken slivers, eggs, shredded cabbage, carrots, bell peppers, dark soy, and chili garlic.",
            rating = 4.7f,
            prepTimeMinutes = 20
        ),
        FoodItem(
            id = 210L,
            name = "Crispy Fried Chicken",
            category = "Starters",
            isVeg = false,
            price = 179.00,
            imageResId = R.drawable.dish_biryani,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1626645738196-c2a7c87a8f58?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1562967914-608f82629710?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1569691899455-884d436c3ab0?w=800&auto=format&fit=crop"
            ),
            vendorName = "Crispy Crunch Hub",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Tender fresh chicken pieces double-dredged in seasoned buttermilk and a 12-spice coating, fried to a golden crunch. Ultra juicy inside.",
            rating = 4.8f,
            prepTimeMinutes = 18
        ),
        FoodItem(
            id = 211L,
            name = "Chicken Burger",
            category = "Burgers & Sandwiches",
            isVeg = false,
            price = 129.00,
            imageResId = R.drawable.dish_biryani,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1586190848861-99aa4a171e90?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1550547660-d9450f859349?w=800&auto=format&fit=crop"
            ),
            vendorName = "The American Diner",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Crispy seasoned chicken patty stacked in a toasted brioche bun with cheddar cheese, crunchy lettuce, sliced pickles, and creamy house burger mayo.",
            rating = 4.7f,
            prepTimeMinutes = 15
        ),
        FoodItem(
            id = 212L,
            name = "Chicken Pizza",
            category = "Pizza & Fast Food",
            isVeg = false,
            price = 249.00,
            imageResId = R.drawable.dish_biryani,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1534308983496-4fabb1a015ee?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=800&auto=format&fit=crop"
            ),
            vendorName = "Oven Fresh Pizzeria",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Crisp hand-tossed base loaded with zesty tandoori chicken tikka chunks, barbecue drizzle, melted mozzarella cheese, red onions, and jalapenos.",
            rating = 4.8f,
            prepTimeMinutes = 25
        ),
        FoodItem(
            id = 213L,
            name = "Chicken Seekh Kebab",
            category = "Starters",
            isVeg = false,
            price = 199.00,
            imageResId = R.drawable.dish_biryani,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1599488615731-7e5c2823ff28?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1603360946369-dc9bb6258143?w=800&auto=format&fit=crop"
            ),
            vendorName = "Tandoor Nights",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Minced chicken blended with fresh coriander, mint, ginger, green chilies, and aromatic spices, roasted on long iron skewers over charcoal embers.",
            rating = 4.8f,
            prepTimeMinutes = 20
        ),
        FoodItem(
            id = 214L,
            name = "Egg Curry",
            category = "Curries",
            isVeg = false,
            price = 119.00,
            imageResId = R.drawable.dish_biryani,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1540420773420-3366772f4999?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1588166524941-3bf61a9c41db?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1567184109191-3783b3218ce7?w=800&auto=format&fit=crop"
            ),
            vendorName = "Desi Dhaba",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Two hard-boiled farm eggs lightly pan-fried in turmeric and chili, served in a rich and fragrant caramelized onion-tomato spiced curry.",
            rating = 4.6f,
            prepTimeMinutes = 15
        ),
        FoodItem(
            id = 215L,
            name = "Chicken Roll",
            category = "Snacks & Street Food",
            isVeg = false,
            price = 119.00,
            imageResId = R.drawable.dish_biryani,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=800&auto=format&fit=crop"
            ),
            vendorName = "Kolkata Roll Corner",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Flaky golden paratha layered with an egg, stuffed with spiced juicy chicken pieces, crunchy sliced red onions, green chillies, and a squeeze of lime.",
            rating = 4.7f,
            prepTimeMinutes = 12
        ),

        // ==================== COFFEE ITEMS (5 Items) ====================
        FoodItem(
            id = 301L,
            name = "Cappuccino",
            category = "Coffee & Beverages",
            isVeg = true,
            price = 119.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1572442388796-11668a67e53d?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1534778101976-62847782c213?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1517256064527-09c73fc73e38?w=800&auto=format&fit=crop"
            ),
            vendorName = "Café Brew World",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Rich full-bodied espresso topped with equal parts silky steamed milk and a thick layer of velvety foam, lightly dusted with cocoa.",
            rating = 4.8f,
            prepTimeMinutes = 8
        ),
        FoodItem(
            id = 302L,
            name = "Café Latte",
            category = "Coffee & Beverages",
            isVeg = true,
            price = 129.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1570968915860-54d5c301fa9f?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1541167760496-1628856ab772?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?w=800&auto=format&fit=crop"
            ),
            vendorName = "Café Brew World",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Double shot of premium dark roast espresso blended with warm micro-foamed milk and finished with elegant artisan latte art.",
            rating = 4.9f,
            prepTimeMinutes = 8
        ),
        FoodItem(
            id = 303L,
            name = "Espresso",
            category = "Coffee & Beverages",
            isVeg = true,
            price = 79.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1510591509098-f4fdc6d0ff04?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1579992357154-faf4bde95b3d?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?w=800&auto=format&fit=crop"
            ),
            vendorName = "Café Brew World",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Intense, concentrated single shot of freshly ground 100% Arabica beans extracted under 15-bar pressure with a dense hazelnut crema.",
            rating = 4.7f,
            prepTimeMinutes = 5
        ),
        FoodItem(
            id = 304L,
            name = "Cold Coffee",
            category = "Coffee & Beverages",
            isVeg = true,
            price = 129.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1517701550927-30cf4ba1dba5?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1556881286-fc6915169721?w=800&auto=format&fit=crop"
            ),
            vendorName = "Café Brew World",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Classic chilled thick cold coffee whipped with double espresso shot, chilled whole milk, vanilla ice cream scoop, and chocolate sauce swirl.",
            rating = 4.9f,
            prepTimeMinutes = 6
        ),
        FoodItem(
            id = 305L,
            name = "Filter Coffee",
            category = "Coffee & Beverages",
            isVeg = true,
            price = 59.00,
            imageResId = R.drawable.dish_veg_thali,
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1521302080334-4bebac2763a6?w=800&auto=format&fit=crop",
                "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=800&auto=format&fit=crop"
            ),
            vendorName = "Udupi Cafe",
            vendorCity = "Vadodara",
            vendorState = "Gujarat",
            available = true,
            description = "Traditional South Indian Kumbakonam filter kaapi brewed with freshly ground chicory-infused coffee decoction and frothed milk in a brass davarah.",
            rating = 4.9f,
            prepTimeMinutes = 5
        )
    )
}
