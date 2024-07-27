package com.application.foodapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.application.foodapp.databinding.SigningInDialogPopupBinding

class OrderConfirmationDialog : DialogFragment() {
    private lateinit var binding: SigningInDialogPopupBinding

    @SuppressLint("UseGetLayoutInflater", "SetTextI18n")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(context)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.signing_in_dialog_popup, null, false)

        binding.signingInText.text = "Confirming Order..."

        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        return dialog
    }
}