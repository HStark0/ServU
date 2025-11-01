package com.app.servu

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText

class EditProfileActivity : AppCompatActivity() {

    private lateinit var profileImageView: ShapeableImageView
    private lateinit var nameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var cpfEditText: TextInputEditText

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            profileImageView.setImageURI(it)
            // In a real app, you would upload this image and save the URL
            val account = GoogleSignIn.getLastSignedInAccount(this)
            if (account != null) {
                val userId = account.id
                val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("user_profile_image_uri_$userId", it.toString())
                    apply()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        profileImageView = findViewById(R.id.profile_image)
        nameEditText = findViewById(R.id.name_edit_text)
        emailEditText = findViewById(R.id.email_edit_text)
        val locationEditText = findViewById<TextInputEditText>(R.id.location_edit_text) // Added location
        cpfEditText = findViewById(R.id.cpf_edit_text)
        val saveButton = findViewById<MaterialButton>(R.id.save_button)

        loadUserData()

        profileImageView.setOnClickListener {
            selectImageLauncher.launch("image/*")
        }

        saveButton.setOnClickListener {
            saveUserData()
            finish()
        }
    }

    private fun loadUserData() {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            val userId = account.id
            val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            
            val firstName = sharedPref.getString("user_first_name_$userId", "")
            val lastName = sharedPref.getString("user_last_name_$userId", "")
            val cpf = sharedPref.getString("user_cpf_$userId", "")
            val imageUriString = sharedPref.getString("user_profile_image_uri_$userId", null)

            nameEditText.setText("$firstName $lastName".trim())
            emailEditText.setText(account.email)
            cpfEditText.setText(cpf)
            if (imageUriString != null) {
                profileImageView.setImageURI(Uri.parse(imageUriString))
            }
        }
    }

    private fun saveUserData() {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            val userId = account.id
            val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

            val fullName = nameEditText.text.toString()
            val nameParts = fullName.split(" ", limit = 2)
            val firstName = nameParts.getOrNull(0) ?: ""
            val lastName = nameParts.getOrNull(1) ?: ""

            with(sharedPref.edit()) {
                putString("user_first_name_$userId", firstName)
                putString("user_last_name_$userId", lastName)
                putString("user_cpf_$userId", cpfEditText.text.toString())
                // The image URI is saved when the user selects it
                apply()
            }
        }
    }
}