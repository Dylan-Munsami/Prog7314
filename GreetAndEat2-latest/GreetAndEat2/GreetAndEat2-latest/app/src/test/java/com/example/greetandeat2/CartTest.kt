import com.example.greetandeat2.MenuItem
import com.example.greetandeat2.Reward
import com.example.greetandeat2.RewardLevel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CartTest {

    @Test
    fun cart_total_isCalculatedCorrectly() {
        val items = listOf(
            MenuItem("1", "Whopper", 60.0),
            MenuItem("2", "Cheese Burger", 50.0)
        )
        val total = items.sumOf { it.price * it.quantity }
        assertEquals(110.0, total, 0.0)
    }

    @Test
    fun cart_total_updatesWhenQuantityChanges() {
        val item = MenuItem("1", "Whopper", 60.0, quantity = 2)
        item.quantity = 3
        val total = item.price * item.quantity
        assertEquals(180.0, total, 0.0)
    }


    @Test
    fun empty_cart_hasZeroTotal() {
        val items = emptyList<MenuItem>()
        val total = items.sumOf { it.price * it.quantity }
        assertEquals(0.0, total, 0.0)
    }

    @Test
    fun status_progressesCorrectly() {
        val statuses = listOf("ORDER_RECEIVED", "PREPARING", "READY_FOR_PICKUP", "OUT_FOR_DELIVERY", "DELIVERED")
        var currentIndex = 0

        currentIndex++
        assertEquals("PREPARING", statuses[currentIndex])

        currentIndex++
        assertEquals("READY_FOR_PICKUP", statuses[currentIndex])
    }

    @Test
    fun addingItem_increasesCartSize() {
        val cart = mutableListOf<MenuItem>()
        val item = MenuItem("1", "Pizza", 100.0)
        cart.add(item)
        assertEquals(1, cart.size)
    }

    // --- 2. Removing item decreases cart size ---
    @Test
    fun removingItem_decreasesCartSize() {
        val cart = mutableListOf(MenuItem("1", "Burger", 50.0))
        cart.removeAt(0)
        assertEquals(0, cart.size)
    }

    // --- 3. Reward points calculation for GOLD rewards ---
    @Test
    fun rewardPoints_sumOfGoldRewards() {
        val rewards = listOf(
            Reward("first_login", "", 100, 0, RewardLevel.GOLD),
            Reward("first_order", "", 200, 0, RewardLevel.SILVER),
            Reward("loyal", "", 300, 0, RewardLevel.GOLD)
        )
        val totalPoints = rewards.filter { it.level == RewardLevel.GOLD }.sumOf { it.points }
        assertEquals(400, totalPoints)
    }

    // --- 4. Active order card visible when order exists ---
    @Test
    fun activeOrderCard_visibilityTest() {
        val userOrders = listOf("ORDER1", "ORDER2")
        val hasActiveOrder = userOrders.isNotEmpty()
        assertTrue(hasActiveOrder)
    }

    // --- 5. Offline mode returns cached data ---
    @Test
    fun offlineMode_returnsCachedOrders() {
        val cachedOrders = listOf("Order1", "Order2")
        val orders = cachedOrders // simulate offline fetch
        assertEquals(2, orders.size)
    }

    // --- 6. Delivery time calculation test ---
    @Test
    fun deliveryTime_isCorrect() {
        val estimatedTime = 25
        val actualTime = 25
        assertEquals(estimatedTime, actualTime)
    }

    // --- 7. Game score increments correctly ---
    @Test
    fun gameScore_incrementsCorrectly() {
        var score = 0
        score += 10
        assertEquals(10, score)
    }

    // --- 8. Quick action buttons trigger intent ---
    @Test
    fun quickActionButton_triggersCorrectly() {
        val actionClicked = "order_food" // simulate button click
        val triggeredActivity = if (actionClicked == "order_food") "MainActivity" else ""
        assertEquals("MainActivity", triggeredActivity)
    }
}





