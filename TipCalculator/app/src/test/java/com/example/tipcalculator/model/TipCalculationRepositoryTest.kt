package com.example.tipcalculator.model

import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.time.Instant

class TipCalculationRepositoryTest{

    lateinit var repository: TipCalculationRepository

    @get:Rule
    val rule:TestRule= InstantTaskExecutorRule()

    @Before
    fun setup(){
        repository= TipCalculationRepository()
    }

    @Test
    fun testSaveTip(){
        val tip=TipCalculation(
            locationName = "7amada",checkAmount = 10.00,tipPct = 25,tipAmount = 2.50,grandTotal = 12.50)

        repository.saveTipCalculation(tip)

        assertEquals(tip,repository.loadTipCalculationByName(tip.locationName ))
    }

    @Test
    fun testLoadSavedTipCalculations(){
        val tip1=TipCalculation(
            locationName = "7amada",checkAmount = 10.00,tipPct = 25,tipAmount = 2.50,grandTotal = 12.50)
        val tip2=TipCalculation(
            locationName = "7amada2",checkAmount = 20.00,tipPct = 25,tipAmount = 5.00 ,grandTotal = 25.00)

        repository.saveTipCalculation(tip1)
        repository.saveTipCalculation(tip2)

        repository.loadSavedTipCalculations().observeForever{
            assertEquals(2,it?.size)

        }
    }
}