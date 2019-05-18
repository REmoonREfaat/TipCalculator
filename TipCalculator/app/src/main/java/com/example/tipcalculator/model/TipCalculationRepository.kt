package com.example.tipcalculator.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

class TipCalculationRepository {

    private val savedTips = mutableMapOf<String,TipCalculation>()
    fun saveTipCalculation(tc: TipCalculation) {
        savedTips[tc.locationName] = tc
    }

    fun loadTipCalculationByName(locationName: String): TipCalculation? {
        return savedTips[locationName]
    }

    fun loadSavedTipCalculations(): LiveData<List<TipCalculation>> {
        val liveDate= MutableLiveData<List<TipCalculation>>()
        liveDate.value=savedTips.values.toList()
        return liveDate
    }
}