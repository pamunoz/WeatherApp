package com.pfariasmunoz.weatherapp

import org.junit.Assert.assertEquals
import org.junit.Test

class OrderingOperationsTest {

    private val unsortedList = listOf(3, 2, 7, 5)

    /**
     * Returns a list with elements in reversed order.
     */
    @Test
    fun reversedTest() {
        assertEquals(listOf(5, 7, 2, 3), unsortedList.reversed())
    }

    /**
     * Returns a sorted list of all elements.
     */
    @Test
    fun sortedTest() {
        assertEquals(listOf(2, 3, 5, 7), unsortedList.sorted())
    }

    /**
     * Returns a list of all elements, sorted by the specified comparator.
     */
    @Test
    fun sortByTest() {
        assertEquals(listOf(3, 7, 2, 5), unsortedList.sortedBy { it % 3 })
    }

    /**
     * Returns a sorted list of all elements, in descending order.
     */
    @Test
    fun sortedDescendingTest() {
        assertEquals(listOf(7, 5, 3, 2), unsortedList.sortedDescending())
    }
    /**
     * Returns a sorted list of all elements, in descending order by the results of the specified
    order function.
     */
    @Test
    fun sortedByDescending() {
        assertEquals(listOf(2, 5, 7, 3), unsortedList.sortedByDescending { it % 3 })
    }


}