package com.app.servu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

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
        val statusTextView: TextView = view.findViewById(R.id.service_status)
        val ratingUnavailableTextView: TextView = view.findViewById(R.id.rating_unavailable_text)
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
        holder.statusTextView.text = item.status

        if (item.status.contains("cancelado", ignoreCase = true)) {
            holder.ratingBar.visibility = View.GONE
            holder.ratingUnavailableTextView.visibility = View.VISIBLE
            holder.statusTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_block, 0)
        } else {
            holder.ratingBar.visibility = View.VISIBLE
            holder.ratingUnavailableTextView.visibility = View.GONE
            holder.statusTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle, 0)
        }

        holder.detailsButton.setOnClickListener {
            onDetailsClick(item)
        }
    }

    override fun getItemCount() = historyItems.size
}