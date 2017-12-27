package com.pfariasmunoz.weatherapp

import org.junit.Test
import org.junit.Assert.*

class MappingOperationsTest {
    private val list = listOf(1, 2, 3, 4, 5, 6)

    /**
     * Iterates over the elements creating a new collection for each one, and finally flattens
    all the collections into a unique list containing all the elements.
     */
    @Test
    fun flatMapTest() {
        assertEquals(listOf(1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7), list.flatMap { listOf(it, it + 1) })
    }
    /**
     * Returns a map of the elements in original collection grouped by the result of given
    function
     */
    @Test
    fun groupByTest() {
        assertEquals(mapOf("odd" to listOf(1, 3, 5), "even" to listOf(2, 4, 6)),
                list.groupBy { if (it % 2 == 0) "even" else "odd" })
    }
    /**
     * Returns a list containing the results of applying the given transform function to each
    element of the original collection.
     */
    @Test
    fun mapTest() {
        assertEquals(listOf(2, 4, 6, 8, 10, 12), list.map { it * 2 })
    }
    /**
     * Returns a list containing the results of applying the given transform function to each
    element and its index of the original collection.
     */
    @Test
    fun mapIndexedTest() {
        assertEquals(listOf(0, 2, 6, 12, 20, 30), list.mapIndexed { index, it -> index * it })
    }
    /**
     * Returns a list containing the results of applying the given transform function to each
    non-null element of the original collection.
     */
    @Test
    fun mapNotNullTest() {
        val listWithNull = listOf(1, 2, 3, 4)
        assertEquals(listOf(2, 4, 6, 8), listWithNull.mapNotNull { it * 2 })
    }

}