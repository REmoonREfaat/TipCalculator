package com.example.tipcalculator.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import com.example.tipcalculator.R

class SaveDialogFragment : DialogFragment() {

    interface Callback {
        fun onSaveTip(name: String)
    }

    private var saveTipCallback: SaveDialogFragment.Callback? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        saveTipCallback=context as? Callback
    }

    override fun onDetach() {
        super.onDetach()
        saveTipCallback=null
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val saveDialog = context?.let {
            val dialogView = createView(it)

            val  editText = dialogView.findViewById<EditText>(R.id.et_location_name)

            AlertDialog.Builder(it)
                .setView(dialogView)
                .setTitle(R.string.positive)
                .setNegativeButton(R.string.negative, null)
                .setPositiveButton(R.string.positive) { _, _ -> onSave(editText) }
                .create()

        }
        return saveDialog!!
    }

    private fun onSave(editText: EditText) {
        val text = editText.text.toString()
        if (text.isNotEmpty()) {
            saveTipCallback?.onSaveTip(text)
        }

    }

    companion object {
        var viewId = View.generateViewId()
    }

    private fun createView(context: Context): View {
        return LayoutInflater.from(context)
            .inflate(R.layout.layout_dialog_save, null)
    }
}