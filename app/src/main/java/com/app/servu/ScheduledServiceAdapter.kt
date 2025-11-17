package com.app.servu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScheduledServiceAdapter(
    private val services: List<ScheduledService>,
    private val onItemClick: (ScheduledService) -> Unit
) : 
    RecyclerView.Adapter<ScheduledServiceAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val serviceName: TextView = view.findViewById(R.id.service_name)
        val providerName: TextView = view.findViewById(R.id.provider_name)
        val dateTime: TextView = view.findViewById(R.id.date_time)
        val paymentInfo: TextView = view.findViewById(R.id.payment_info)
        val serviceValue: TextView = view.findViewById(R.id.service_value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_scheduled_service, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = services[position]
        holder.serviceName.text = service.serviceName
        holder.providerName.text = service.providerName
        holder.dateTime.text = "${service.date} - ${service.time}" // Corrected to show time
        holder.paymentInfo.text = "${service.paymentMethod} - ${service.paymentCondition}"
        holder.serviceValue.text = service.serviceValue

        holder.itemView.setOnClickListener { onItemClick(service) }
    }

    override fun getItemCount() = services.size
}