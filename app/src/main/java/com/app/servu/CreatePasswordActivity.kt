package com.app.servu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class CreatePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_password)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        val continueButton = findViewById<MaterialButton>(R.id.continue_button)
        val passwordEditText = findViewById<TextInputEditText>(R.id.password_edit_text)
        val confirmPasswordEditText = findViewById<TextInputEditText>(R.id.confirm_password_edit_text)

        continueButton.isEnabled = false

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = passwordEditText.text.toString()
                val confirmPassword = confirmPasswordEditText.text.toString()
                continueButton.isEnabled = password.isNotBlank() && password == confirmPassword
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        passwordEditText.addTextChangedListener(textWatcher)
        confirmPasswordEditText.addTextChangedListener(textWatcher)

        continueButton.setOnClickListener {
            val email = intent.getStringExtra("email")
            val password = passwordEditText.text.toString()

            val sharedPref = getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString(email, password) // Use email as key
                apply()
            }

            val intent = Intent(this, PersonalDataActivity::class.java)
            intent.putExtra("email", email)
            startActivity(intent)
        }
    }
}