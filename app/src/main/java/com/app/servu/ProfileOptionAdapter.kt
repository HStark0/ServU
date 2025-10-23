package com.app.servu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class ProfileOption(val icon: Int, val title: String)

class ProfileOptionAdapter(private val options: List<ProfileOption>) :
    RecyclerView.Adapter<ProfileOptionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.option_icon)
        val title: TextView = view.findViewById(R.id.option_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_profile_option, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val option = options[position]
        holder.icon.setImageResource(option.icon)
        holder.title.text = option.title
    }

    override fun getItemCount() = options.size
}