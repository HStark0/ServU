package com.app.servu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class HistoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scheduledItem = view.findViewById<View>(R.id.scheduled_item)
        val detailsButton = scheduledItem.findViewById<TextView>(R.id.details_button)
        val rateButton = scheduledItem.findViewById<MaterialButton>(R.id.rate_button)

        detailsButton.setOnClickListener {
            showDetailsDialog()
        }

        rateButton.setOnClickListener {
            val intent = Intent(context, RatingActivity::class.java)
            startActivity(intent)
        }

        // TODO: Add logic to populate the RecyclerView and handle clicks for other items
    }

    private fun showDetailsDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_service_details, null)

        builder.setView(dialogView)
        val dialog = builder.create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }
}