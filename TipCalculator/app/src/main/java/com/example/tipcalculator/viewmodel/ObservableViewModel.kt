package com.example.tipcalculator.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry

abstract class ObservableViewModel (app:Application):AndroidViewModel(app),Observable{

    @delegate:Transient
    private val mCallBacks:PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        mCallBacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        mCallBacks.remove(callback)
    }

    fun notifyChange(){
        mCallBacks.notifyChange(this,0)
    }
}