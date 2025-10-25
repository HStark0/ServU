package com.app.servu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class SearchResult(val name: String, val rating: Float, val specialty: String, val services: Int, val imageResId: Int)

class SearchResultAdapter(private val results: List<SearchResult>, private val onClick: (SearchResult) -> Unit) :
    RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val providerImage: ImageView = view.findViewById(R.id.provider_image)
        val providerName: TextView = view.findViewById(R.id.provider_name)
        val providerRating: TextView = view.findViewById(R.id.provider_rating)
        val providerSpecialty: TextView = view.findViewById(R.id.provider_specialty)
        val providerServices: TextView = view.findViewById(R.id.provider_services)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.providerImage.setImageResource(result.imageResId)
        holder.providerName.text = result.name
        holder.providerRating.text = result.rating.toString()
        holder.providerSpecialty.text = result.specialty
        holder.providerServices.text = "Serviços: ${result.services}"

        holder.itemView.setOnClickListener { onClick(result) }
    }

    override fun getItemCount() = results.size
}