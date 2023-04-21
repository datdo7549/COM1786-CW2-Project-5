package com.example.comp1786cw2project2.feature

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.example.com1786_cw2project1.R


class CustomInputDialog(context: Context, private val listener: OnInputListener) : DialogFragment() {

    private lateinit var editTextInput: EditText


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create a new dialog with the desired width
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE);
        return dialog
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_custom_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add your logic here

        editTextInput = view.findViewById(R.id.edit_text_input)
        val buttonOk = view.findViewById<AppCompatButton>(R.id.button_ok)

        // Set click listener for OK button
        buttonOk.setOnClickListener {
            // Call listener's onInput method with the entered text
            listener.onInput(editTextInput.text.toString())
            // Dismiss the dialog
            dismiss()
        }
    }

    interface OnInputListener {
        fun onInput(text: String)
    }
}