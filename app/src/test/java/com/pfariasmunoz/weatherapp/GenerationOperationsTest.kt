package com.pfariasmunoz.weatherapp

import org.junit.Test
import org.junit.Assert.*

class GenerationOperationsTest {
    private val list = listOf(1, 2, 3, 4, 5, 6)
    private val listRepeated = listOf(2, 2, 3, 4, 5, 5, 6)

    /**
     * Returns a list of values built from elements of both collections with same indexes
    using the provided transform function. The list has the length of shortest collection.
     */
    @Test
    fun zipTest() {
        assertEquals(listOf(3, 4, 6, 8, 10, 11), list.zip(listRepeated) { it1, it2 -> it1 + it2 })
    }
    /**
     * Splits original collection into pair of collections, where the first collection contains
    elements for which the predicate returned true , while the second collection contains
    elements for which the predicate returned false .
     */
    @Test
    fun partitionTest() {
        assertEquals(Pair(listOf(2, 4, 6), listOf(1, 3, 5)),
                list.partition { it % 2 == 0 })
    }

    /**
     * Returns a list containing all elements of the original collection and then all elements
    of the given collection. Because of the name of the function, we can use the ‘+’
    operator with it.
     */
    @Test
    fun plusTest() {
        assertEquals(listOf(1, 2, 3, 4, 5, 6, 7, 8), list + listOf(7, 8))
    }

    /**
     * Generates a Pair of List s from a List of Pairs
     */
    @Test
    fun unzipTest() {
        assertEquals(Pair(listOf(5, 6), listOf(7, 8)),
        listOf(Pair(5, 7), Pair(6, 8)).unzip())
    }
}
