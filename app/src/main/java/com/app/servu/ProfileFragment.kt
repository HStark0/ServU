package com.app.servu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileOptionsRecyclerView = view.findViewById<RecyclerView>(R.id.profile_options_recycler_view)
        profileOptionsRecyclerView.layoutManager = LinearLayoutManager(context)

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