package com.learn.universities

import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class FibonacciTest {


    @Test
    fun testfibonacci_series() {
        val result=Fibonacci.Fibonacci_series(10)
        assertEquals(55,result)
    }
}