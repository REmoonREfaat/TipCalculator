package com.example.tipcalculator.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import java.math.RoundingMode

//business logic
class Calculator(val repository: TipCalculationRepository= TipCalculationRepository()) {

    fun calculateTip(checkAmount: Double, tipPct: Int): TipCalculation {
        val tipAmount: Double =( checkAmount * (tipPct.toDouble() / 100.0))
            .toBigDecimal()
            .setScale(2,RoundingMode.HALF_UP)
            .toDouble()

        val grandTotal: Double = tipAmount + checkAmount
        return TipCalculation(checkAmount = checkAmount,
            tipPct = tipPct, tipAmount = tipAmount,grandTotal =  grandTotal)
    }

    fun saveTipCalculation(tc: TipCalculation) {
        repository.saveTipCalculation(tc)
    }

    fun loadTipCalculationByName(locationName: String): TipCalculation? {
        return repository.loadTipCalculationByName(locationName)
    }

    fun loadSavedTipCalculations(): LiveData<List<TipCalculation>> {
        return repository.loadSavedTipCalculations()
    }
}