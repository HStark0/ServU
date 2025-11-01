package com.app.servu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.button.MaterialButton

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

        val profileNameTextView = view.findViewById<TextView>(R.id.profile_name)
        val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val lastUserEmail = sharedPref.getString("last_logged_in_user", null)

        if (lastUserEmail != null) {
            val firstName = sharedPref.getString("user_first_name_$lastUserEmail", "Usuário")
            val lastName = sharedPref.getString("user_last_name_$lastUserEmail", "")
            profileNameTextView.text = "$firstName $lastName".trim()
        } else {
            val account = GoogleSignIn.getLastSignedInAccount(requireActivity())
            if (account != null) {
                val userId = account.id
                val firstName = sharedPref.getString("user_first_name_$userId", "Usuário")
                val lastName = sharedPref.getString("user_last_name_$userId", "")
                profileNameTextView.text = "$firstName $lastName".trim()
            }
        }

        val profileHeader = view.findViewById<LinearLayout>(R.id.profile_header)
        profileHeader.setOnClickListener {
            val intent = Intent(context, EditProfileActivity::class.java)
            startActivity(intent)
        }

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

        val signOutButton = view.findViewById<MaterialButton>(R.id.sign_out_button)
        signOutButton.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        // Sign out from Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        googleSignInClient.signOut().addOnCompleteListener(requireActivity()) { 
            // Clear last logged in user
            val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                remove("last_logged_in_user")
                apply()
            }

            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}