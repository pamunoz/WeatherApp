package com.pfariasmunoz.weatherapp.algorithms

import org.junit.Test

class What {


    @Test
    fun printSomething() {
        println("Function One")
        println("================================")
        println("${fOne(99999999L).toBigInteger()}")
        println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨")
        println("Function Two")
        println("================================")
        println("${fTwo(99999999L).toBigInteger()}")
        println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨")
        println("Function Three")
        println("================================")
        println("${fThree(99999999L).toBigInteger()}")
        println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨")
        println("Function Four")
        println("================================")
        println("${fFour(99999999L).toBigInteger()}")
        println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨")
    }

    private fun Long.pow(n: Long): Long {
        return Math.pow(this.toDouble(), n.toDouble()).toLong()
    }

    private fun fOne(n: Long): Long {
        return n.pow(6L)
    }

    private fun fTwo(n: Long): Long {
        return 2L.pow(n)
    }

    private fun fThree(n: Long): Long {
        return 3L.pow(n)
    }

    private fun fFour(n: Long): Long {
        return n.pow(n)
    }
}