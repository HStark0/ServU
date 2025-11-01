package com.app.servu

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class VerifyCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_code)

        val title = findViewById<TextView>(R.id.title)
        val continueButton = findViewById<MaterialButton>(R.id.continueButton)
        val verificationType = intent.getStringExtra("verificationType")
        val identifier = intent.getStringExtra("phoneNumber") // This is used for phone number or email

        val code1 = findViewById<EditText>(R.id.code1)
        val code2 = findViewById<EditText>(R.id.code2)
        val code3 = findViewById<EditText>(R.id.code3)
        val code4 = findViewById<EditText>(R.id.code4)
        val code5 = findViewById<EditText>(R.id.code5)
        val code6 = findViewById<EditText>(R.id.code6)
        val codeFields = listOf(code1, code2, code3, code4, code5, code6)

        continueButton.isEnabled = false

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val allFilled = codeFields.all { it.text.isNotBlank() }
                continueButton.isEnabled = allFilled
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        codeFields.forEach { it.addTextChangedListener(textWatcher) }

        if (verificationType == "e-mail") {
            title.text = "Digite o código de 6 dígitos que enviamos para o seu $identifier"
        } else {
            title.text = "Digite o código de 6 dígitos que enviamos por $verificationType para $identifier"
        }

        continueButton.setOnClickListener {
            if (verificationType == "WhatsApp" || verificationType == "SMS") {
                val intent = Intent(this, CreateAccountEmailActivity::class.java)
                startActivity(intent)
            } else { // This is now the email verification case
                val intent = Intent(this, CreatePasswordActivity::class.java)
                intent.putExtra("email", identifier) // Pass the email to the next screen
                startActivity(intent)
            }
        }
    }
}