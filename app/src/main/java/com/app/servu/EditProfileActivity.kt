package com.app.servu

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
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

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            profileImageView.setImageURI(it)
            copyAndSaveImage(it)
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

        loadUserData(true) // TEMPORARY: Force clear name
        toggleEditMode(isEditMode) // Set initial state

        val photoClickListener = View.OnClickListener {
            selectImageLauncher.launch("image/*")
        }
        profileImageView.setOnClickListener(photoClickListener)
        editPhotoLabel.setOnClickListener(photoClickListener)

        saveButton.setOnClickListener {
            saveUserData()
            isEditMode = false
            toggleEditMode(isEditMode)
            invalidateOptionsMenu()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.edit_profile_menu, menu)
        val editMenuItem = menu.findItem(R.id.action_edit)
        // Change icon to 'done' when in edit mode
        editMenuItem.setIcon(if (isEditMode) R.drawable.ic_done else R.drawable.ic_edit)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                isEditMode = !isEditMode
                toggleEditMode(isEditMode)
                invalidateOptionsMenu() // Redraw menu to update icon
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
    }

    private fun getUserId(): String? {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val manualUserEmail = sharedPref.getString("last_logged_in_user", null)
        if (manualUserEmail != null) {
            return manualUserEmail
        }
        val googleAccount = GoogleSignIn.getLastSignedInAccount(this)
        return googleAccount?.id
    }

    private fun loadUserData(clearName: Boolean = false) {
        val userId = getUserId() ?: return
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        val email = sharedPref.getString("user_email_$userId", "")
        val cpf = sharedPref.getString("user_cpf_$userId", "")
        val imagePath = sharedPref.getString("user_profile_image_path_$userId", null)

        if (clearName) {
            nameEditText.setText("")
        } else {
            val firstName = sharedPref.getString("user_first_name_$userId", "")
            val lastName = sharedPref.getString("user_last_name_$userId", "")
            nameEditText.setText("$firstName $lastName".trim())
        }
        
        emailEditText.setText(email)
        cpfEditText.setText(cpf)
        if (imagePath != null) {
            profileImageView.setImageURI(Uri.fromFile(File(imagePath)))
        }
    }

    private fun saveUserData() {
        val userId = getUserId() ?: return
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        val fullName = nameEditText.text.toString()
        val nameParts = fullName.split(" ", limit = 2)
        val firstName = nameParts.getOrNull(0) ?: ""
        val lastName = nameParts.getOrNull(1) ?: ""

        with(sharedPref.edit()) {
            putString("user_first_name_$userId", firstName)
            putString("user_last_name_$userId", lastName)
            putString("user_email_$userId", emailEditText.text.toString())
            putString("user_cpf_$userId", cpfEditText.text.toString())
            apply()
        }
    }

    private fun copyAndSaveImage(uri: Uri) {
        val userId = getUserId() ?: return
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        val fileName = "profile_image_$userId.jpg"
        val destinationFile = File(filesDir, fileName)

        try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(destinationFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            with(sharedPref.edit()) {
                putString("user_profile_image_path_$userId", destinationFile.absolutePath)
                apply()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}