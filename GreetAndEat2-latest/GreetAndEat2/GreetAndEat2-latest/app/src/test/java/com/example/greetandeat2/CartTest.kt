import com.example.greetandeat2.MenuItem
import org.junit.Assert.assertEquals
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

}



