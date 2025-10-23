package com.app.servu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val profileOptionsRecyclerView = findViewById<RecyclerView>(R.id.profile_options_recycler_view)
        profileOptionsRecyclerView.layoutManager = LinearLayoutManager(this)

        val options = listOf(
            ProfileOption(R.drawable.ic_notifications, "Notificações"),
            ProfileOption(R.drawable.ic_credit_card, "Pagamentos"),
            ProfileOption(R.drawable.ic_favorite, "Favoritos"),
            ProfileOption(R.drawable.ic_address, "Endereços"),
            ProfileOption(R.drawable.ic_help, "Ajuda"),
            ProfileOption(R.drawable.ic_settings, "Configurações")
        )

        profileOptionsRecyclerView.adapter = ProfileOptionAdapter(options)
    }
}