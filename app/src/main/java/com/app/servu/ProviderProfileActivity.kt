package com.app.servu

import android.content.Context
import android.os.Build
import android.os.Bundle
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
            finish() // Finish if no provider data is found
            return
        }

        setupToolbar()
        populateProviderData()
        setupServicesRecyclerView()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
}