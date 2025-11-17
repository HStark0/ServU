package com.app.servu

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProviderProfileActivity : AppCompatActivity(), 
    ScheduleDialogFragment.ScheduleDialogListener, 
    PaymentDialogFragment.PaymentDialogListener {

    private var tempServiceName: String? = null
    private var tempDate: String? = null
    private var tempTime: String? = null
    private var tempLocation: String? = null
    private var tempServiceValue: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_profile)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        setupServiceButton("Manutenção", R.id.item_servico_manutencao, "R$ 500,00")
        setupServiceButton("Instalações", R.id.item_servico_instalacoes, "R$ 300,00")
        setupServiceButton("Iluminação", R.id.item_servico_iluminacao, "R$ 190,00")
    }

    private fun setupServiceButton(serviceName: String, layoutId: Int, serviceValue: String) {
        val serviceLayout = findViewById<View>(layoutId)
        val scheduleButton = serviceLayout.findViewById<Button>(R.id.schedule_button)
        scheduleButton.setOnClickListener {
            tempServiceName = serviceName
            tempServiceValue = serviceValue
            showScheduleDialog()
        }
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
        val service = ScheduledService(
            serviceName = tempServiceName!!,
            providerName = "Matheus Santos - Eletricista", // Hardcoded for now
            date = tempDate!!,
            time = tempTime!!,
            location = tempLocation!!,
            paymentMethod = paymentMethod,
            paymentCondition = paymentCondition,
            serviceValue = tempServiceValue!!
        )

        saveScheduledService(service)

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