package com.app.servu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.File

class ProfileFragment : Fragment() {

    private val db = Firebase.firestore
    private val auth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateProfileData(view)

        val profileHeader = view.findViewById<LinearLayout>(R.id.profile_header)
        profileHeader.setOnClickListener {
            val intent = Intent(context, EditProfileActivity::class.java)
            startActivity(intent)
        }

        val profileOptionsRecyclerView = view.findViewById<RecyclerView>(R.id.profile_options_recycler_view)
        profileOptionsRecyclerView.layoutManager = LinearLayoutManager(context)

        val options = listOf(
            ProfileOption(R.drawable.ic_credit_card, "Pagamentos"),
            ProfileOption(R.drawable.ic_favorite, "Favoritos"),
            ProfileOption(R.drawable.ic_address, "Endereços"),
            ProfileOption(R.drawable.ic_help, "Ajuda"),
            ProfileOption(R.drawable.ic_settings, "Configurações")
        )

        profileOptionsRecyclerView.adapter = ProfileOptionAdapter(options)

        val signOutButton = view.findViewById<Button>(R.id.sign_out_button)
        signOutButton.setOnClickListener {
            signOut()
        }
    }

    override fun onResume() {
        super.onResume()
        view?.let { updateProfileData(it) }
    }

    private fun getUserId(): String? {
        return auth.currentUser?.uid
    }

    private fun updateProfileData(view: View) {
        val profileNameTextView = view.findViewById<TextView>(R.id.profile_name)
        val profileImageView = view.findViewById<ImageView>(R.id.profile_image)
        
        val userId = getUserId()
        if (userId == null) {
            // If user is not logged in, redirect to MainActivity
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            return
        }

        db.collection("users").document(userId).get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val user = document.toObject(User::class.java)
                user?.let {
                    profileNameTextView.text = "${it.firstName} ${it.lastName}".trim()
                    if (it.profileImagePath != null) {
                        profileImageView.setImageURI(Uri.fromFile(File(it.profileImagePath)))
                    } else {
                        profileImageView.setImageResource(R.drawable.ic_profile_placeholder)
                    }
                }
            } else {
                profileNameTextView.text = "Usuário"
                profileImageView.setImageResource(R.drawable.ic_profile_placeholder)
            }
        }
    }

    private fun signOut() {
        // Sign out from Firebase Auth
        auth.signOut()

        // Sign out from Google as well, if applicable
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        GoogleSignIn.getClient(requireActivity(), gso).signOut().addOnCompleteListener { 
            // This part runs after Google sign-out is complete
            // Clear any local preferences that might cause issues
            val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            sharedPref.edit().clear().apply()

            // Go to MainActivity
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}