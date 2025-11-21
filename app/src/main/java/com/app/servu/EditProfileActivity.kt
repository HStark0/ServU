package com.app.servu

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.File
import java.io.FileOutputStream

class EditProfileActivity : AppCompatActivity() {

    private lateinit var profileImageView: ShapeableImageView
    private lateinit var editPhotoLabel: TextView
    private lateinit var nameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var cpfEditText: TextInputEditText
    private lateinit var locationEditText: TextInputEditText
    private lateinit var saveButton: MaterialButton

    private var isEditMode = false
    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private var currentImagePath: String? = null

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            profileImageView.setImageURI(it)
            copyImageToInternalStorage(it) // This will just update the local path
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        profileImageView = findViewById(R.id.profile_image)
        editPhotoLabel = findViewById(R.id.edit_photo_label)
        nameEditText = findViewById(R.id.name_edit_text)
        emailEditText = findViewById(R.id.email_edit_text)
        locationEditText = findViewById(R.id.location_edit_text)
        cpfEditText = findViewById(R.id.cpf_edit_text)
        saveButton = findViewById(R.id.save_button)

        loadUserData()
        toggleEditMode(isEditMode)

        val photoClickListener = View.OnClickListener {
            if (isEditMode) {
                selectImageLauncher.launch("image/*")
            }
        }
        profileImageView.setOnClickListener(photoClickListener)
        editPhotoLabel.setOnClickListener(photoClickListener)

        saveButton.setOnClickListener {
            saveUserData()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.edit_profile_menu, menu)
        val editMenuItem = menu.findItem(R.id.action_edit)
        editMenuItem.setIcon(if (isEditMode) R.drawable.ic_done else R.drawable.ic_edit)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                if (isEditMode) { // If we were in edit mode, save the data
                    saveUserData()
                } else { // If we were not in edit mode, just toggle to it
                    isEditMode = true
                    toggleEditMode(isEditMode)
                    invalidateOptionsMenu()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleEditMode(enabled: Boolean) {
        nameEditText.isEnabled = enabled
        emailEditText.isEnabled = enabled
        cpfEditText.isEnabled = enabled
        locationEditText.isEnabled = enabled
        saveButton.visibility = if (enabled) View.VISIBLE else View.GONE
        editPhotoLabel.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    private fun getUserId(): String? {
        return auth.currentUser?.uid
    }

    private fun loadUserData() {
        val userId = getUserId() ?: return
        db.collection("users").document(userId).get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val user = document.toObject(User::class.java)
                user?.let {
                    nameEditText.setText("${it.firstName} ${it.lastName}".trim())
                    emailEditText.setText(it.email)
                    cpfEditText.setText(it.cpf)
                    if (it.profileImagePath != null) {
                        currentImagePath = it.profileImagePath // Store the initial path
                        profileImageView.setImageURI(Uri.fromFile(File(it.profileImagePath)))
                    }
                }
            }
        }
    }

    private fun saveUserData() {
        val userId = getUserId() ?: return

        val fullName = nameEditText.text.toString()
        val nameParts = fullName.split(" ", limit = 2)
        val firstName = nameParts.getOrNull(0) ?: ""
        val lastName = nameParts.getOrNull(1) ?: ""
        val email = emailEditText.text.toString()
        val cpf = cpfEditText.text.toString()

        val userData = mutableMapOf<String, Any?>(
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "cpf" to cpf,
            "profileImagePath" to currentImagePath // Always save the most recent image path
        )

        db.collection("users").document(userId).update(userData)
            .addOnSuccessListener { 
                Toast.makeText(this, "Perfil salvo com sucesso!", Toast.LENGTH_SHORT).show()
                isEditMode = false
                toggleEditMode(isEditMode)
                invalidateOptionsMenu()
            }
            .addOnFailureListener { e ->
                Log.w("EditProfileActivity", "Error updating document", e)
                Toast.makeText(this, "Erro ao salvar perfil: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun copyImageToInternalStorage(uri: Uri) {
        val userId = getUserId() ?: return
        val fileName = "profile_image_$userId.jpg"
        val destinationFile = File(filesDir, fileName)

        try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(destinationFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            // ONLY update the local variable. The save operation will handle the database.
            currentImagePath = destinationFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Erro ao processar a imagem.", Toast.LENGTH_SHORT).show()
        }
    }
}