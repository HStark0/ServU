package com.app.servu

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val historyFragment = HistoryFragment()
    private val profileFragment = ProfileFragment()
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            toolbar.visibility = View.VISIBLE
            loadFragment(homeFragment)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    toolbar.visibility = View.VISIBLE
                    loadFragment(homeFragment)
                    true
                }
                R.id.navigation_search -> {
                    toolbar.visibility = View.GONE
                    loadFragment(searchFragment)
                    true
                }
                R.id.navigation_recent -> {
                    toolbar.visibility = View.GONE
                    loadFragment(historyFragment)
                    true
                }
                R.id.navigation_profile -> {
                    toolbar.visibility = View.GONE
                    loadFragment(profileFragment)
                    true
                }
                else -> false
            }
        }

        findViewById<ImageView>(R.id.notifications_icon).setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        findViewById<ImageView>(R.id.chat_icon).setOnClickListener {
            startActivity(Intent(this, ChatListActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        updateWelcomeMessage()
    }

    private fun updateWelcomeMessage() {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val lastUserId = sharedPref.getString("last_logged_in_user", null)
        val userFirstName = if (lastUserId != null) {
            sharedPref.getString("user_first_name_$lastUserId", "Usuário")
        } else {
             val account = GoogleSignIn.getLastSignedInAccount(this)
             account?.givenName ?: "Usuário"
        }
        
        val welcomeText = "Bem vindo, "
        val spannable = SpannableString("$welcomeText$userFirstName")
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.custom_accent_green)),
            welcomeText.length, 
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        toolbar.title = spannable
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }
}