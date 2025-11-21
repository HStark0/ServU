package com.app.servu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PersonalDataActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val auth = Firebase.auth

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
            val password = intent.getStringExtra("password")

            if (email != null && password != null) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val firebaseUser = auth.currentUser
                            val uid = firebaseUser!!.uid
                            
                            val firstName = firstNameEditText.text.toString()
                            val lastName = lastNameEditText.text.toString()
                            val cpf = cpfEditText.text.toString()

                            val user = User(
                                uid = uid,
                                firstName = firstName,
                                lastName = lastName,
                                email = email,
                                cpf = cpf
                            )

                            db.collection("users").document(uid).set(user)
                                .addOnSuccessListener { 
                                    val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                                    with(sharedPref.edit()) {
                                        putString("last_logged_in_user", uid)
                                        apply()
                                    }
                                    
                                    val intent = Intent(this, HomeActivity::class.java)
                                    finishAffinity()
                                    startActivity(intent)
                                }
                                .addOnFailureListener { e ->
                                    Log.w("PersonalDataActivity", "Error adding document", e)
                                    Toast.makeText(baseContext, "Falha ao salvar dados: ${e.message}", Toast.LENGTH_LONG).show()
                                }
                        } else {
                            Log.w("PersonalDataActivity", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Falha na autenticação: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }
}