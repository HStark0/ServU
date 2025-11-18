package com.app.servu

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProviderProfileActivity : AppCompatActivity(),
    ScheduleDialogFragment.ScheduleDialogListener,
    PaymentDialogFragment.PaymentDialogListener {

    private var provider: Provider? = null
    private var isFavorite: Boolean = false
    
    // Temporary data for the scheduling flow
    private var tempService: ProviderService? = null
    private var tempDate: String? = null
    private var tempTime: String? = null
    private var tempLocation: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_profile)

        provider = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("provider", Provider::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("provider")
        }

        if (provider == null) {
            finish()
            return
        }

        isFavorite = isProviderFavorite(provider!!.id)

        setupToolbar()
        populateProviderData()
        setupServicesRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.provider_profile_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val favoriteItem = menu.findItem(R.id.action_favorite)
        favoriteItem.setIcon(if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                toggleFavoriteStatus()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun toggleFavoriteStatus() {
        isFavorite = !isFavorite
        updateFavoriteProvider(provider!!.id, isFavorite)
        invalidateOptionsMenu()
        val message = if (isFavorite) "Adicionado aos favoritos" else "Removido dos favoritos"
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun populateProviderData() {
        findViewById<TextView>(R.id.provider_profile_name).text = "${provider!!.name} - ${provider!!.specialty}"
        findViewById<TextView>(R.id.provider_description).text = provider!!.description
        findViewById<ImageView>(R.id.provider_profile_image).setImageResource(provider!!.profileImageResId)
        findViewById<ImageView>(R.id.provider_background_image).setImageResource(provider!!.backgroundImageResId)
    }

    private fun setupServicesRecyclerView() {
        val servicesRecyclerView = findViewById<RecyclerView>(R.id.provider_services_recycler_view)
        servicesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = ProviderServiceAdapter(provider!!.services) { service ->
            tempService = service
            showScheduleDialog()
        }
        servicesRecyclerView.adapter = adapter
    }

    // --- Scheduling Logic (Restored) ---

    private fun showScheduleDialog() {
        val dialog = ScheduleDialogFragment()
        dialog.listener = this
        dialog.show(supportFragmentManager, "ScheduleDialogFragment")
    }

    override fun onDateAndLocationSet(date: String, time: String, location: String) {
        tempDate = date
        tempTime = time
        tempLocation = location
        
        val paymentDialog = PaymentDialogFragment()
        paymentDialog.listener = this
        paymentDialog.show(supportFragmentManager, "PaymentDialogFragment")
    }

    override fun onPaymentConfirmed(paymentMethod: String, paymentCondition: String) {
        val finalService = ScheduledService(
            serviceName = tempService!!.name,
            providerName = provider!!.name,
            date = tempDate!!,
            time = tempTime!!,
            location = tempLocation!!,
            paymentMethod = paymentMethod,
            paymentCondition = paymentCondition,
            serviceValue = tempService!!.value
        )

        saveScheduledService(finalService)
        Toast.makeText(this, "Agendamento concluído com sucesso!", Toast.LENGTH_LONG).show()
    }

    private fun saveScheduledService(service: ScheduledService) {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPref.getString("scheduled_services", "[]")
        val type = object : TypeToken<MutableList<ScheduledService>>() {}.type
        val scheduledServices: MutableList<ScheduledService> = gson.fromJson(json, type)

        scheduledServices.add(0, service)

        with(sharedPref.edit()) {
            putString("scheduled_services", gson.toJson(scheduledServices))
            apply()
        }
    }

    // --- Favorites Logic ---

    private fun isProviderFavorite(providerId: String): Boolean {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val favoriteIds = sharedPref.getStringSet("favorite_providers", emptySet()) ?: emptySet()
        return favoriteIds.contains(providerId)
    }

    private fun updateFavoriteProvider(providerId: String, isFavorite: Boolean) {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val favoriteIds = sharedPref.getStringSet("favorite_providers", emptySet())?.toMutableSet() ?: mutableSetOf()
        if (isFavorite) {
            favoriteIds.add(providerId)
        } else {
            favoriteIds.remove(providerId)
        }
        with(sharedPref.edit()) {
            putStringSet("favorite_providers", favoriteIds)
            apply()
        }
    }
}