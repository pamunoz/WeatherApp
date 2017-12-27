package com.pfariasmunoz.weatherapp

import org.junit.Test
import org.junit.Assert.*

class ElementsOperationsTest {

    private val list = listOf(1, 2, 3, 4, 5, 6)

    /**
     * Returns true if the element is found in the collection.
     */
    @Test
    fun containsTest() {
        assertTrue(list.contains(2))
    }
    /**
     * Returns an element at the given index or throws an IndexOutOfBoundsException if
    the index is out of bounds of this collection.
     */
    @Test
    fun elementAtTest() {
        assertEquals(2, list.elementAt(1))
    }
    /**
     * Returns an element at the given index or the result of calling the default function if
    the index is out of bounds of this collection.
     */
    @Test
    fun elementAtOrElseTest() {
        assertEquals(20, list.elementAtOrElse(10, {2 * it}))
    }
    /**
     * Returns an element at the given index or null if the index is out of bounds of this
    collection.
     */
    @Test
    fun elementAtOrNull() {
        assertNull(list.elementAtOrNull(10))
    }
    /**
     * Returns the first element matching the given predicate. It will throw a NoSuchEle-
    mentException if no elements are found.
     */
    @Test
    fun firstTest() {
        assertEquals(2, list.first { it % 2 == 0 })
    }
    /**
     * Returns the first element matching the given predicate, or null if no element was
    found.
     */
    @Test
    fun firstOrNullTest() {
        assertNull(list.firstOrNull { it % 7 == 0 })
    }

    /**
     * Returns the first index of element, or -1 if the collection does not contain element.
     */
    @Test
    fun indexOfTest() {
        assertEquals(3, list.indexOf(4))
    }
    /**
     * Returns index of the first element matching the given predicate, or -1 if the collection
    does not contain such element.
     */
    @Test
    fun indexOfFirstTest() {
        assertEquals(1, list.indexOfFirst { it % 2 == 0 })
    }

    /**
     * Returns index of the last element matching the given predicate, or -1 if the collection
    does not contain such element.
     */
    @Test
    fun indexOfLastTest() {
        assertEquals(5, list.indexOfLast { it % 2 == 0 })
    }

    /**
     * Returns the last element matching the given predicate. It will throw a NoSuchEle-
    mentException if no elements are found.
     */
    @Test
    fun lastTest() {
        assertEquals(6, list.last { it % 2 == 0 })
    }
    /**
     * Returns last index of element, or -1 if the collection does not contain element.
     */
    @Test
    fun lastIndexOfTest() {
        val listRepeated = listOf(2, 2, 3, 4, 5, 5, 6)
        assertEquals(5, listRepeated.lastIndexOf(5))
    }

    /**
     * Returns the last element matching the given predicate, or null if no such element
    was found.
     */
    @Test
    fun lastOrNullTest() {
        assertNull(list.lastOrNull { it % 7 == 0 })
    }

    /**
     * Returns the single element matching the given predicate, or throws exception if there
    is no or more than one matching element.
     */
    @Test
    fun singleTest() {
        assertEquals(5, list.single { it % 5 == 0 })
    }

    /**
     * Returns the single element matching the given predicate, or null if element was not
    found or more than one element was found.
     */
    @Test
    fun singleOrNullTest() {
        assertNull(list.singleOrNull { it % 7 == 0 })
    }
}