package com.app.servu

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class CreateAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account_phone)

        val whatsappButton = findViewById<MaterialButton>(R.id.whatsappButton)
        val smsButton = findViewById<MaterialButton>(R.id.smsButton)
        val phoneEditText = findViewById<TextInputEditText>(R.id.phoneEditText)

        // Disable buttons initially
        whatsappButton.isEnabled = false
        smsButton.isEnabled = false

        phoneEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isPhoneEntered = s?.isNotBlank() ?: false
                whatsappButton.isEnabled = isPhoneEntered
                smsButton.isEnabled = isPhoneEntered
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        whatsappButton.setOnClickListener {
            val intent = Intent(this, VerifyCodeActivity::class.java)
            intent.putExtra("verificationType", "WhatsApp")
            intent.putExtra("phoneNumber", phoneEditText.text.toString())
            startActivity(intent)
        }

        smsButton.setOnClickListener {
            val intent = Intent(this, VerifyCodeActivity::class.java)
            intent.putExtra("verificationType", "SMS")
            intent.putExtra("phoneNumber", phoneEditText.text.toString())
            startActivity(intent)
        }
    }
}