package com.example.tipcalculator.model

import com.example.tipcalculator.BaseTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

class CalculatorTest : BaseTest() {
    lateinit var calculator: Calculator

    @Mock
    lateinit var repository: TipCalculationRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        calculator = Calculator(repository)
    }

    @Test
    fun testCalculateTip() {

        val baseTc = TipCalculation(checkAmount = 10.00)

        val testValues = listOf(
            baseTc.copy(tipPct = 25, tipAmount = 2.50, grandTotal = 12.50),
            baseTc.copy(tipPct = 18, tipAmount = 1.80, grandTotal = 11.80),
            baseTc.copy(tipPct = 15, tipAmount = 1.50, grandTotal = 11.50)
        )

        testValues.forEach {
            assertEquals(
                it,
                calculator.calculateTip(it.checkAmount, it.tipPct)
            )
        }
    }

    @Test
    fun testSaveTipCalculation() {
        calculator.saveTipCalculation(TipCalculation())
        Mockito.verify(repository, times(1)).saveTipCalculation(any(TipCalculation::class.java))
    }


    @Test
    fun testLoadTipCalculationByName() {
        repository.loadTipCalculationByName("7amada")
        Mockito.verify(repository, times(1)).loadTipCalculationByName("7amada")
    }

    @Test
    fun testLoadSavedTipCalculations() {
        repository.loadSavedTipCalculations()
        Mockito.verify(repository, times(1))
            .loadSavedTipCalculations()
    }
}