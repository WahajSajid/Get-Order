package com.application.foodapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.application.foodapp.databinding.SelectQuantityDialogPopupBinding

class QuantityDialogBoxPopup : DialogFragment() {
    private lateinit var binding: SelectQuantityDialogPopupBinding

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(context)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.select_quantity_dialog_popup, null, false)

        binding.plusImageButton.setOnClickListener {
            val quantity = binding.selectQuantityBoxEditText.text.toString().toInt() + 1
            binding.selectQuantityBoxEditText.setText(quantity.toString())
        }
        binding.minusImageButton.setOnClickListener {
            if (binding.selectQuantityBoxEditText.text.toString().toInt() > 1) {
                val quantity = binding.selectQuantityBoxEditText.text.toString().toInt() - 1
                binding.selectQuantityBoxEditText.setText(quantity.toString())
            } else Toast.makeText(context,"Minimum quantity could be 1",Toast.LENGTH_SHORT).show()
        }
        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        return dialog
    }
}