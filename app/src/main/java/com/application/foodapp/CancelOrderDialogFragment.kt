package com.application.foodapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.application.foodapp.databinding.CancelOrderDialogFragmentBinding

class CancelOrderDialogFragment:DialogFragment() {
    private lateinit var binding:CancelOrderDialogFragmentBinding
    private val sharedViewModel:SharedViewModel by activityViewModels()
    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater  = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater,R.layout.cancel_order_dialog_fragment,null,false)


        val  app = requireContext().applicationContext as MyApp

        binding.yesButton.setOnClickListener {
            app.cancelOrderOrNot = true
            dismiss()
            (activity as? ConfirmOrderActivity)?.finish()
        }

        binding.noButton.setOnClickListener {
            app.cancelOrderOrNot = false
            dismiss()
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        return dialog
    }
}