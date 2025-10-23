package com.app.servu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class PersonalDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)

        val createAccountFinalButton = findViewById<MaterialButton>(R.id.createAccountFinalButton)

        createAccountFinalButton.setOnClickListener {
            // TODO: Add logic to save user data
            val intent = Intent(this, HomeActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }
    }
}