package com.application.foodapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.application.foodapp.databinding.SigningInDialogPopupBinding

class SigningInDialogBox : DialogFragment() {
    private lateinit var binding: SigningInDialogPopupBinding
    private val sharedViewModel:SharedViewModel by activityViewModels()

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(context)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.signing_in_dialog_popup, null, false)
        val app = requireActivity().application as MyApp
        if(app.dismissOrNot){
            dismiss()
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        return dialog
    }
}