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
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            Log.d("MainActivity", "Google Sign-In successful for: ${account.email}")
            firebaseAuthWithGoogle(account)
        } catch (e: ApiException) {
            Log.w("MainActivity", "Google sign in failed with code: ${e.statusCode}", e)
            Toast.makeText(this, "Falha no login com Google. Código: ${e.statusCode}", Toast.LENGTH_LONG).show()
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser!!
                    db.collection("users").document(user.uid).get().addOnSuccessListener { document ->
                        if (!document.exists()) {
                            val newUser = User(
                                uid = user.uid,
                                firstName = account.givenName ?: "",
                                lastName = account.familyName ?: "",
                                email = account.email ?: "",
                            )
                            db.collection("users").document(user.uid).set(newUser).addOnSuccessListener {
                                navigateToHome()
                            }
                        } else {
                            navigateToHome()
                        }
                    }
                } else {
                    Log.w("MainActivity", "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Falha na autenticação com Firebase.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        if (auth.currentUser != null) {
            navigateToHome()
            return
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
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
            val email = emailOrPhoneEditText.text.toString()
            val password = passwordEditText.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        navigateToHome()
                    } else {
                        Log.w("MainActivity", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        val googleLoginButton = findViewById<MaterialButton>(R.id.googleLoginButton)
        googleLoginButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        finishAffinity()
        startActivity(intent)
    }
}