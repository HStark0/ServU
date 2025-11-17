package com.app.servu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProviderAdapter(
    private val providers: List<Provider>,
    private val onProviderClick: (Provider) -> Unit
) : RecyclerView.Adapter<ProviderAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.provider_image)
        val name: TextView = view.findViewById(R.id.provider_name)
        val specialty: TextView = view.findViewById(R.id.provider_specialty)
        val rating: RatingBar = view.findViewById(R.id.provider_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_provider, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val provider = providers[position]
        holder.name.text = provider.name
        holder.specialty.text = provider.specialty
        holder.rating.rating = provider.rating
        holder.image.setImageResource(provider.profileImageResId)

        holder.itemView.setOnClickListener { onProviderClick(provider) }
    }

    override fun getItemCount() = providers.size
}