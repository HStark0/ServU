package com.app.servu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeFragment : Fragment() {

    private lateinit var scheduledRecyclerView: RecyclerView
    private lateinit var scheduledServicesTitle: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        scheduledRecyclerView = view.findViewById(R.id.home_scheduled_recycler_view)
        scheduledServicesTitle = view.findViewById(R.id.scheduled_services_title)
        scheduledRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        
        setupCategoryClicks(view)
        
        return view
    }

    override fun onResume() {
        super.onResume()
        loadScheduledServices()
    }

    private fun setupCategoryClicks(view: View) {
        view.findViewById<View>(R.id.category_maintenance).setOnClickListener {
            openProviderList("Manutenção")
        }
        view.findViewById<View>(R.id.category_transport).setOnClickListener {
            openProviderList("Transporte")
        }
        // Add other categories here in the future
    }

    private fun openProviderList(category: String) {
        val intent = Intent(context, ProviderListActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }

    private fun loadScheduledServices() {
        val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val scheduledServicesJson = sharedPref.getString("scheduled_services", "[]")
        val type = object : TypeToken<List<ScheduledService>>() {}.type
        val scheduledServices: List<ScheduledService> = gson.fromJson(scheduledServicesJson, type)

        if (scheduledServices.isNotEmpty()) {
            scheduledServicesTitle.visibility = View.VISIBLE
            scheduledRecyclerView.visibility = View.VISIBLE
            scheduledRecyclerView.adapter = HomeScheduledServiceAdapter(scheduledServices)
        } else {
            scheduledServicesTitle.visibility = View.GONE
            scheduledRecyclerView.visibility = View.GONE
        }
    }
}