package com.app.servu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchResultAdapter(
    private val providers: List<Provider>,
    private val onItemClicked: (Provider) -> Unit
) : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val providerImage: ImageView = view.findViewById(R.id.provider_image)
        val providerName: TextView = view.findViewById(R.id.provider_name)
        val providerRating: TextView = view.findViewById(R.id.provider_rating)
        val providerSpecialty: TextView = view.findViewById(R.id.provider_specialty)
        val providerServicesCount: TextView = view.findViewById(R.id.provider_services_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val provider = providers[position]
        holder.providerImage.setImageResource(provider.profileImageResId)
        holder.providerName.text = "${provider.name} - ${provider.specialty}"
        holder.providerRating.text = provider.rating.toString()
        holder.providerSpecialty.text = "Especialista em ${provider.category.lowercase()}"
        holder.providerServicesCount.text = "Serviços: ${provider.services.size}"
        holder.itemView.setOnClickListener { onItemClicked(provider) }
    }

    override fun getItemCount() = providers.size
}