package com.app.servu

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        val clearHistoryButton = findViewById<Button>(R.id.clear_history_button)
        clearHistoryButton.setOnClickListener {
            showClearHistoryConfirmationDialog()
        }
    }

    private fun showClearHistoryConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Limpar Histórico")
            .setMessage("Tem certeza que deseja apagar permanentemente todo o seu histórico de serviços? Esta ação não pode ser desfeita.")
            .setPositiveButton("Sim, limpar") { _, _ ->
                clearHistory()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun clearHistory() {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            remove("history_services")
            apply()
        }
        Toast.makeText(this, "Histórico de serviços foi limpo.", Toast.LENGTH_SHORT).show()
    }
}