package com.app.servu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class CreateAccountEmailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account_email)

        val continueButton = findViewById<MaterialButton>(R.id.continueButton)
        val emailEditText = findViewById<TextInputEditText>(R.id.emailEditText)

        continueButton.setOnClickListener {
            val intent = Intent(this, VerifyCodeActivity::class.java)
            intent.putExtra("verificationType", "e-mail")
            intent.putExtra("phoneNumber", emailEditText.text.toString()) // Although the key is phoneNumber, we are passing the email
            startActivity(intent)
        }
    }
}