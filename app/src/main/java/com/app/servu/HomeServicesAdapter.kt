package com.app.servu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomeServicesAdapter(private val items: List<HomeItem>) : 
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_OPTIONS = 0
        private const val TYPE_SCHEDULED = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is HomeItem.ServiceOptions -> TYPE_OPTIONS
            is HomeItem.Scheduled -> TYPE_SCHEDULED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_OPTIONS -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_home_service_options, parent, false)
                OptionsViewHolder(view)
            }
            TYPE_SCHEDULED -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_home_scheduled_service, parent, false)
                ScheduledViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ScheduledViewHolder -> {
                val scheduledItem = items[position] as HomeItem.Scheduled
                holder.bind(scheduledItem.service)
            }
            // OptionsViewHolder does not need binding
        }
    }

    override fun getItemCount() = items.size

    class OptionsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class ScheduledViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val serviceName: TextView = view.findViewById(R.id.service_name)
        private val dateTime: TextView = view.findViewById(R.id.date_time)
        private val providerName: TextView = view.findViewById(R.id.provider_name)

        fun bind(service: ScheduledService) {
            serviceName.text = service.serviceName
            dateTime.text = "${service.date} - ${service.time}"
            providerName.text = service.providerName
        }
    }
}