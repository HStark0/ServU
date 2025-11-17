package com.app.servu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Updated data class to include status
data class HistoryItem(
    val providerName: String, 
    val serviceDescription: String, 
    val rating: Float, 
    val status: String
)

class HistoryAdapter(
    private val historyItems: List<HistoryItem>,
    private val onDetailsClick: (HistoryItem) -> Unit
) : 
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val providerName: TextView = view.findViewById(R.id.provider_name)
        val serviceDescription: TextView = view.findViewById(R.id.service_description)
        val ratingBar: RatingBar = view.findViewById(R.id.rating_bar)
        val detailsButton: TextView = view.findViewById(R.id.details_button)
        val statusTextView: TextView = view.findViewById(R.id.service_status) // Get status TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = historyItems[position]
        holder.providerName.text = item.providerName
        holder.serviceDescription.text = item.serviceDescription
        holder.ratingBar.rating = item.rating
        holder.statusTextView.text = item.status // Set the status text

        // Hide rating bar if the service was canceled
        if (item.status.contains("cancelado")) {
            holder.ratingBar.visibility = View.GONE
        } else {
            holder.ratingBar.visibility = View.VISIBLE
        }

        holder.detailsButton.setOnClickListener {
            onDetailsClick(item)
        }
    }

    override fun getItemCount() = historyItems.size
}