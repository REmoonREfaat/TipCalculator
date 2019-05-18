package com.example.tipcalculator.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.databinding.BaseObservable
import android.util.Log
import com.example.tipcalculator.R
import com.example.tipcalculator.model.Calculator
import com.example.tipcalculator.model.TipCalculation

class CalculatorViewModel @JvmOverloads constructor(
    app: Application, val calculator: Calculator = Calculator()
) : ObservableViewModel(app) {

    private var lastTipCalculated = TipCalculation()

    //    inputs
    var inputCheckAmount = ""
    var inputTipPct = ""

    //    output
    val outputCheckAmount
        get() = getApplication<Application>().getString(
            R.string.dollar_formatter,
            lastTipCalculated.checkAmount
        )
    val outputTipAmount
        get() = getApplication<Application>().getString(
            R.string.dollar_formatter,
            lastTipCalculated.tipAmount
        )
    val outputGrandTotal
        get() = getApplication<Application>().getString(
            R.string.dollar_formatter,
            lastTipCalculated.grandTotal
        )
    val locationName get() = lastTipCalculated.locationName

    init {
        updateOutputs(TipCalculation())
    }

    private fun updateOutputs(tipCalculation: TipCalculation) {
        lastTipCalculated = tipCalculation
        notifyChange()
    }

    fun saveCurrentTip(name: String) {
        val tipToSave = lastTipCalculated.copy(locationName = name)
        calculator.saveTipCalculation(tipToSave)

        updateOutputs(tipToSave)
    }

    fun calculateTip() {
        //Log.e("CalculatorViewModel", "calculateTip invoked")
        var checkAmount = inputCheckAmount.toDoubleOrNull()
        var tipPct = inputTipPct.toIntOrNull()

        if (checkAmount != null && tipPct != null) {
//            Log.e("CalculatorViewModel","check Amount = $checkAmount, percentage = $tipPct")
            updateOutputs(calculator.calculateTip(checkAmount, tipPct))
        }
    }

    fun clearInputs() {
        inputCheckAmount = "0.00"
        inputTipPct = "0"
        notifyChange()
    }

    fun loadSavedTipCalculationSummaries(): LiveData<List<TipCalculationSummaryItem>> {

        //1-load saved tip calculations using calculator object = savedItems
        var savedItems = calculator.loadSavedTipCalculations()

        //2-covert savedItems from list of TipCalculation items  to
        // list of TipCalculationSummaryItem items = summaryItems
        var summaryItems = Transformations.map(savedItems) { tipCalculationObjects ->
            tipCalculationObjects.map {
                TipCalculationSummaryItem(
                    it.locationName,
                    getApplication<Application>().getString(R.string.dollar_formatter, it.grandTotal)
                )
            }
        }

        //3-return summaryItems
        return summaryItems
    }

    fun loadCalculation(name: String){
        val tc= calculator.loadTipCalculationByName(name)

        if (tc!=null){
            inputCheckAmount= tc.checkAmount.toString()
            inputTipPct= tc.tipPct.toString()

            updateOutputs(tc)
            notifyChange()
        }
    }
}