package com.app.servu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient

    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            Log.d("MainActivity", "Google Sign-In successful for: ${account.email}")

            saveGoogleUserData(account)
            checkProfileAndNavigate(account)

        } catch (e: ApiException) {
            Log.w("MainActivity", "Google sign in failed with code: ${e.statusCode}", e)
        }
    }
    
    private fun saveGoogleUserData(account: GoogleSignInAccount) {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = account.id ?: return

        with(sharedPref.edit()) {
            putString("last_logged_in_user", userId) // Save Google ID as last logged in

            // Save user details using Google ID as key
            putString("user_first_name_$userId", account.givenName ?: "")
            putString("user_last_name_$userId", account.familyName ?: "")
            putString("user_email_$userId", account.email ?: "")
            
            apply()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val lastUserId = sharedPref.getString("last_logged_in_user", null)

        if (lastUserId != null) {
            val isProfileComplete = sharedPref.getBoolean("profile_complete_$lastUserId", false)
            if (isProfileComplete) {
                navigateToHome()
                return // Skip the rest of onCreate
            }
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val bottomSheet = findViewById<LinearLayout>(R.id.bottomSheet)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        val loginButton = findViewById<MaterialButton>(R.id.loginButton)
        loginButton.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        val createAccountButton = findViewById<MaterialButton>(R.id.createAccountButton)
        createAccountButton.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }

        // Bottom Sheet Login Validation
        val bottomSheetLoginButton = findViewById<MaterialButton>(R.id.bottomSheetLoginButton)
        val emailOrPhoneEditText = findViewById<TextInputEditText>(R.id.emailOrPhoneEditText)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        bottomSheetLoginButton.isEnabled = false

        val loginTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val emailOrPhone = emailOrPhoneEditText.text.toString()
                val password = passwordEditText.text.toString()
                bottomSheetLoginButton.isEnabled = emailOrPhone.isNotBlank() && password.isNotBlank()
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        emailOrPhoneEditText.addTextChangedListener(loginTextWatcher)
        passwordEditText.addTextChangedListener(loginTextWatcher)

        bottomSheetLoginButton.setOnClickListener {
            val enteredEmail = emailOrPhoneEditText.text.toString()
            val enteredPassword = passwordEditText.text.toString()

            val credentialsPref = getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
            val savedPassword = credentialsPref.getString(enteredEmail, null) // Use email as key

            if (enteredPassword == savedPassword) {
                with(sharedPref.edit()) {
                    putString("last_logged_in_user", enteredEmail)
                    apply()
                }
                navigateToHome()
            } else {
                Toast.makeText(this, "E-mail ou senha inválidos", Toast.LENGTH_SHORT).show()
            }
        }

        val googleLoginButton = findViewById<MaterialButton>(R.id.googleLoginButton)
        googleLoginButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }

    private fun checkProfileAndNavigate(account: GoogleSignInAccount) {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = account.id ?: return
        val isProfileComplete = sharedPref.getBoolean("profile_complete_$userId", false)
        if (isProfileComplete) {
            navigateToHome()
        } else {
            navigateToPersonalData(account.email)
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        finishAffinity() // Clear the back stack
        startActivity(intent)
    }

    private fun navigateToPersonalData(email: String?) {
        val intent = Intent(this, PersonalDataActivity::class.java)
        intent.putExtra("email", email)
        finishAffinity()
        startActivity(intent)
    }
}