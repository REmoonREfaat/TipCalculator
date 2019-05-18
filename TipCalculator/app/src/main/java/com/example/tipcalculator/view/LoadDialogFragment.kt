package com.example.tipcalculator.view

import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import com.example.tipcalculator.R
import com.example.tipcalculator.viewmodel.CalculatorViewModel
import kotlinx.android.synthetic.main.saved_tip_calculations_list.view.*

class LoadDialogFragment : DialogFragment() {

    interface Callback {
        fun onTipSelected(name: String)
    }

    private var loadTipCallback: LoadDialogFragment.Callback? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        loadTipCallback = context as? Callback
    }

    override fun onDetach() {
        super.onDetach()
        loadTipCallback = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val loadDialog = context?.let {

            AlertDialog.Builder(it)
                .setView(createView(it))
                .setTitle(R.string.load)
                .setNegativeButton(R.string.negative, null)
                .create()
        }
        return loadDialog!!
    }

    private fun createView(context: Context): View {
        val rv = LayoutInflater.from(context)
            .inflate(R.layout.saved_tip_calculations_list, null)
            .rv_tip_calculations_list_item

        rv.setHasFixedSize(true)
        rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        val adapter= TipSummaryAdapter{
            loadTipCallback?.onTipSelected(it.locationName)
            dismiss()
        }
        rv.adapter= adapter

        val vm= ViewModelProviders.of(activity!!).get(CalculatorViewModel::class.java)
        vm.loadSavedTipCalculationSummaries().observe(this, Observer {
            if(it!=null){
                adapter.updateList(it)
            }
        })


        return rv

    }

}