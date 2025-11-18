package com.app.servu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotificationAdapter(private val notifications: List<Notification>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.notification_icon)
        val title: TextView = view.findViewById(R.id.notification_title)
        val message: TextView = view.findViewById(R.id.notification_message)
        val time: TextView = view.findViewById(R.id.notification_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = notifications[position]
        holder.icon.setImageResource(notification.iconResId)
        holder.title.text = notification.title
        holder.message.text = notification.message
        holder.time.text = notification.time
    }

    override fun getItemCount() = notifications.size
}