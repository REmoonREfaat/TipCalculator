package com.example.tipcalculator

import org.mockito.Mockito

open class BaseTest {

    fun <T> any(type : Class<T>): T {
        Mockito.any(type)
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T
}