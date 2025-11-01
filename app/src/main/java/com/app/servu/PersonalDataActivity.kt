package com.app.servu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class PersonalDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)

        val createAccountFinalButton = findViewById<MaterialButton>(R.id.createAccountFinalButton)
        val firstNameEditText = findViewById<TextInputEditText>(R.id.firstNameEditText)
        val lastNameEditText = findViewById<TextInputEditText>(R.id.lastNameEditText)
        val cpfEditText = findViewById<TextInputEditText>(R.id.cpfEditText)

        createAccountFinalButton.isEnabled = false

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val firstName = firstNameEditText.text.toString()
                val lastName = lastNameEditText.text.toString()
                val cpf = cpfEditText.text.toString()
                createAccountFinalButton.isEnabled = firstName.isNotBlank() && lastName.isNotBlank() && cpf.isNotBlank()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        firstNameEditText.addTextChangedListener(textWatcher)
        lastNameEditText.addTextChangedListener(textWatcher)
        cpfEditText.addTextChangedListener(textWatcher)

        createAccountFinalButton.setOnClickListener {
            val email = intent.getStringExtra("email")
            if (email != null) {
                val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putBoolean("profile_complete_$email", true)
                    putString("user_first_name_$email", firstNameEditText.text.toString())
                    putString("user_last_name_$email", lastNameEditText.text.toString())
                    putString("user_cpf_$email", cpfEditText.text.toString())
                    // Save the currently logged in user's email
                    putString("last_logged_in_user", email)
                    apply()
                }
            }

            val intent = Intent(this, HomeActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }
    }
}