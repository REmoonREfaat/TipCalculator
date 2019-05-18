package com.example.tipcalculator.viewmodel

import android.app.Application
import com.example.tipcalculator.R
import com.example.tipcalculator.model.Calculator
import com.example.tipcalculator.model.TipCalculation
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class CalculatorViewModelTest {
    lateinit var calculatorViewModel: CalculatorViewModel
    @Mock
    lateinit var mockCalculator: Calculator
    @Mock
    lateinit var application: Application

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        stubResource(0.0, "$0.00")
        calculatorViewModel = CalculatorViewModel(application, mockCalculator)

    }


    fun stubResource(given: Double, returnValue: String) {
        Mockito.`when`(application.getString(R.string.dollar_formatter, given)).thenReturn(returnValue)
    }

    @Test
    fun testCalculateTip() {
        calculatorViewModel.inputCheckAmount = "10.00"
        calculatorViewModel.inputTipPct = "25"
        val stub = TipCalculation(checkAmount = 10.00, tipAmount = 2.50, grandTotal = 12.50)
        Mockito.`when`(mockCalculator.calculateTip(10.00, 25)).thenReturn(stub)

        stubResource(10.0, "$10.00")
        stubResource(2.5, "$2.50")
        stubResource(12.5, "$12.50")

        calculatorViewModel.calculateTip()

        assertEquals("$10.00", calculatorViewModel.outputCheckAmount)
    }

    @Test
    fun testCalculateTipBadTipPercent() {
        calculatorViewModel.inputCheckAmount = "10.00"
        calculatorViewModel.inputTipPct = ""

        calculatorViewModel.calculateTip()
        Mockito.verify(mockCalculator, never()).calculateTip(
            ArgumentMatchers.anyDouble(), ArgumentMatchers.anyInt()
        )
    }


    @Test
    fun testCalculateTipBadCheckAmount() {
        calculatorViewModel.inputCheckAmount = ""
        calculatorViewModel.inputTipPct = "25"

        calculatorViewModel.calculateTip()

        verify(mockCalculator, never()).calculateTip(
            ArgumentMatchers.anyDouble(), ArgumentMatchers.anyInt()
        )
    }

    @Test
    fun test1SaveCurrentTip() {

        //1-create location name as string
        var locationName = "locationNameTest"
        var tipCalculation = TipCalculation(locationName = locationName)
        //2-verify that calculator.save() is called with the tipCalculation
        //which has the location name
        mockCalculator.saveTipCalculation(tipCalculation)

        verify(mockCalculator, times(1))
            .saveTipCalculation(tipCalculation)
    }

    private  fun  prepareSaveTestStubs(stubLocationName:String):TipCalculation{
        val stub = TipCalculation(checkAmount = 10.00, tipAmount = 2.50, grandTotal = 12.50)

        fun setupTipCalculation(){
            calculatorViewModel.inputCheckAmount="10.00"
            calculatorViewModel.inputTipPct="25"

            Mockito.`when`(mockCalculator.calculateTip(10.00, 25)).thenReturn(stub)
        }

        setupTipCalculation()
        calculatorViewModel.calculateTip()

        calculatorViewModel.saveCurrentTip(stubLocationName)
        return stub
    }

    @Test
    fun testSaveCurrentTipCallsSaveTipCalculation() {
        val stubLocationName="Green"
        val stub =prepareSaveTestStubs(stubLocationName)
        verify(mockCalculator).saveTipCalculation(stub.copy(locationName = stubLocationName))
    }

    @Test
    fun testSaveCurrentTipCallsUpdateOutputs() {
        val stubLocationName="Green"
        val stub =prepareSaveTestStubs(stubLocationName)
        assertEquals(stubLocationName,calculatorViewModel.locationName)
    }
}
