package com.app.servu

import android.content.Intent
import android.os.Bundle
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

        if (verificationType == "e-mail") {
            title.text = "Digite o código de 6 dígitos que enviamos para o seu $identifier"
        } else {
            title.text = "Digite o código de 6 dígitos que enviamos por $verificationType para $identifier"
        }

        continueButton.setOnClickListener {
            if (verificationType == "WhatsApp" || verificationType == "SMS") {
                val intent = Intent(this, CreateAccountEmailActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, PersonalDataActivity::class.java)
                startActivity(intent)
            }
        }
    }
}