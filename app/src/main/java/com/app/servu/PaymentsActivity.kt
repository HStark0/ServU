package com.app.servu

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class PaymentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payments)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        val addCardButton = findViewById<Button>(R.id.add_card_button)
        addCardButton.setOnClickListener {
            showAddCardDialog()
        }
    }

    private fun showAddCardDialog() {
        // In a real app, this would be a more complex screen.
        AlertDialog.Builder(this)
            .setTitle("Adicionar Cartão")
            .setMessage("Funcionalidade de adicionar cartão ainda não implementada.")
            .setPositiveButton("OK", null)
            .show()
    }
}