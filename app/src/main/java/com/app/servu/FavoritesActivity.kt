package com.app.servu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class FavoritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        val recyclerView = findViewById<RecyclerView>(R.id.favorites_recycler_view)
        
        val favoriteProviders = getFavoriteProviders()

        val adapter = ProviderAdapter(favoriteProviders) { provider ->
            val intent = Intent(this, ProviderProfileActivity::class.java)
            intent.putExtra("provider", provider)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }

    private fun getFavoriteProviders(): List<Provider> {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val favoriteIds = sharedPref.getStringSet("favorite_providers", emptySet()) ?: emptySet()
        val allProviders = ProviderListActivity.getAllProviders()
        return allProviders.filter { favoriteIds.contains(it.id) }
    }
}