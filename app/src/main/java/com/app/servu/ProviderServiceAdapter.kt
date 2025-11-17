package com.app.servu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProviderServiceAdapter(
    private val services: List<ProviderService>,
    private val onScheduleClick: (ProviderService) -> Unit
) : RecyclerView.Adapter<ProviderServiceAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.service_image)
        val name: TextView = view.findViewById(R.id.service_name)
        val value: TextView = view.findViewById(R.id.service_price)
        val time: TextView = view.findViewById(R.id.service_time)
        val scheduleButton: Button = view.findViewById(R.id.schedule_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_servico_instalacoes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = services[position]
        holder.name.text = service.name
        holder.value.text = service.value
        holder.time.text = service.time
        holder.image.setImageResource(service.imageResId)

        holder.scheduleButton.setOnClickListener { onScheduleClick(service) }
    }

    override fun getItemCount() = services.size
}