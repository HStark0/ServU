package com.app.servu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomeScheduledServiceAdapter(private val services: List<ScheduledService>) : 
    RecyclerView.Adapter<HomeScheduledServiceAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val serviceName: TextView = view.findViewById(R.id.service_name)
        val dateTime: TextView = view.findViewById(R.id.date_time)
        val providerName: TextView = view.findViewById(R.id.provider_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_scheduled_service, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = services[position]
        holder.serviceName.text = service.serviceName
        holder.dateTime.text = "${service.date} - ${service.time}"
        holder.providerName.text = service.providerName
    }

    override fun getItemCount() = services.size
}