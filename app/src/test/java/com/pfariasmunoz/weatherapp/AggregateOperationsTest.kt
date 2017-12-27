package com.pfariasmunoz.weatherapp

import org.junit.Test

import org.junit.Assert.*


class CollectionsTest() {

    private val list = listOf(1, 2, 3, 4, 5, 6)

    @Test
    fun anyTest() {
        assertTrue(list.any { it % 2 == 0 })
        assertFalse(list.any {it > 10})
    }

    @Test
    fun allTest() {
        assertTrue(list.all { it < 10 })
        assertFalse(list.all {it % 2 == 0})
    }

    @Test
    fun countTest() {
        assertEquals(2, list.count { it % 2 == 0 })
    }

    @Test
    fun foldTest() {
        assertEquals(25, list.fold(4) {total, next -> total + next})
    }

    @Test
    fun foldRightTest() {
        assertEquals(25, list.foldRight(4) { total, next -> total + next })
    }

    @Test
    fun forEachTest() {
        list.forEach { println(it) }
    }

    @Test
    fun forEachIndexed() {
        list.forEachIndexed { index, value
            -> println("position $index constains the $value") }
    }

    @Test
    fun maxTest() {
        assertEquals(6, list.max())
    }

    @Test
    fun maxByTest() {
        // The element whose negative is greater
        assertEquals(1, list.maxBy { -it })
    }

    @Test
    fun minTest() {
        // The element whose negative is smaller
        assertEquals(1, list.min())
    }

    @Test
    fun minByTest() {
        // The element whose negative is smaller
        assertEquals(6, list.minBy { -it })
    }

    @Test
    fun noneTest() {
        // No elements are divisible by 7
        assertTrue(list.none { it % 7 == 0 })
    }

    @Test
    fun reduceTest() {
        assertEquals(21, list.reduce { total, next -> total + next })
    }

    @Test
    fun reduceRightTest() {
        assertEquals(21, list.reduceRight { total, next -> total + next})
    }

    @Test
    fun sumByTest() {
        assertEquals(3, list.sumBy { it % 2 })
    }
}