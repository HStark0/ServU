package com.app.servu

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HistoryFragment : Fragment() {
    private lateinit var scheduledRecyclerView: RecyclerView
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var noScheduledTextView: TextView
    private lateinit var noHistoryTextView: TextView

    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scheduledRecyclerView = view.findViewById(R.id.scheduled_recycler_view)
        historyRecyclerView = view.findViewById(R.id.history_recycler_view)
        noScheduledTextView = view.findViewById(R.id.no_scheduled_text)
        noHistoryTextView = view.findViewById(R.id.no_history_text)

        scheduledRecyclerView.layoutManager = LinearLayoutManager(context)
        historyRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onResume() {
        super.onResume()
        loadScheduledServices()
        loadHistoryServices()
    }

    private fun loadScheduledServices() {
        val scheduledServices = getScheduledServices()

        if (scheduledServices.isNotEmpty()) {
            val adapter = ScheduledServiceAdapter(scheduledServices) { service ->
                showScheduledServiceDetails(service)
            }
            scheduledRecyclerView.adapter = adapter
            scheduledRecyclerView.visibility = View.VISIBLE
            noScheduledTextView.visibility = View.GONE
        } else {
            scheduledRecyclerView.visibility = View.GONE
            noScheduledTextView.visibility = View.VISIBLE
        }
    }

    private fun loadHistoryServices() {
        val historyItems = getHistoryItems()

        val exampleHistory1 = HistoryItem(
            "João Ernesto - Freteiro",
            "Frete de carga pequena",
            3.5f,
            "Serviço completo",
            "Cartão de Crédito",
            "2x",
            "Minha Casa",
            "R$ 150,00"
        )
        if (historyItems.none { it.providerName == exampleHistory1.providerName && it.serviceDescription == exampleHistory1.serviceDescription }) {
            historyItems.add(0, exampleHistory1)
        }
        
        val exampleHistory2 = HistoryItem(
            "Maria Oliveira - Diarista",
            "Limpeza Padrão (3h)",
            4.9f,
            "Serviço concluído",
            "Pix",
            "À vista",
            "Escritório",
            "R$ 150,00"
        )
        if (historyItems.none { it.providerName == exampleHistory2.providerName && it.serviceDescription == exampleHistory2.serviceDescription }) {
            historyItems.add(0, exampleHistory2)
        }

        if (historyItems.isNotEmpty()) {
            val adapter = HistoryAdapter(historyItems) { item ->
                showDetailsDialog(item)
            }
            historyRecyclerView.adapter = adapter
            historyRecyclerView.visibility = View.VISIBLE
            noHistoryTextView.visibility = View.GONE
        } else {
            historyRecyclerView.visibility = View.GONE
            noHistoryTextView.visibility = View.VISIBLE
        }
    }

    private fun showScheduledServiceDetails(service: ScheduledService) {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_scheduled_service_details, null)
        builder.setView(view)
        val dialog = builder.create()

        val serviceName = view.findViewById<TextView>(R.id.detail_service_name)
        val providerName = view.findViewById<TextView>(R.id.detail_provider_name)
        val location = view.findViewById<TextView>(R.id.detail_location)
        val date = view.findViewById<TextView>(R.id.detail_date)
        val value = view.findViewById<TextView>(R.id.detail_value)
        val paymentMethod = view.findViewById<TextView>(R.id.detail_payment_method)
        val concludeButton = view.findViewById<Button>(R.id.conclude_service_button)
        val cancelButton = view.findViewById<Button>(R.id.cancel_service_button)

        serviceName.text = service.serviceName
        providerName.text = "Prestador: ${service.providerName}"
        location.text = "Local: ${service.location}"
        date.text = "Data: ${service.date} - ${service.time}" // Corrected to show time
        value.text = "Valor: ${service.serviceValue}"
        paymentMethod.text = "Pagamento: ${service.paymentMethod} - ${service.paymentCondition}"

        cancelButton.setOnClickListener {
            dialog.dismiss()
            showCancelConfirmationDialog(service)
        }

        concludeButton.setOnClickListener {
            dialog.dismiss()
            if (service.paymentMethod.equals("Pix", ignoreCase = true)) {
                showQrCodeDialog(service)
            } else {
                showConfirmingPaymentDialog(service)
            }
        }
        
        dialog.show()
    }
    
    private fun showQrCodeDialog(service: ScheduledService) {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_qr_code, null)
        builder.setView(view)
        val dialog = builder.create()
        
        val proceedButton = view.findViewById<Button>(R.id.proceed_button)
        proceedButton.setOnClickListener {
            dialog.dismiss()
            showConfirmingPaymentDialog(service)
        }
        
        dialog.show()
    }

    private fun showConfirmingPaymentDialog(service: ScheduledService) {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_confirming_payment, null, false)
        builder.setView(view).setCancelable(false)
        val dialog = builder.create()
        dialog.show()

        Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
            showRatingDialog(service)
        }, 5000)
    }
    
    private fun showRatingDialog(service: ScheduledService) {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_rate_service, null)
        builder.setView(view)
        val dialog = builder.create()

        val ratingBar = view.findViewById<RatingBar>(R.id.service_rating_bar)
        val submitButton = view.findViewById<Button>(R.id.submit_rating_button)

        submitButton.setOnClickListener {
            val rating = ratingBar.rating
            concludeService(service, rating)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun concludeService(service: ScheduledService, rating: Float) {
        // Remove from scheduled list
        val scheduledServices = getScheduledServices()
        scheduledServices.removeIf { it.serviceName == service.serviceName && it.date == service.date } // More specific removal
        saveScheduledServices(scheduledServices)

        // Add to history list with 'concluded' status
        val historyItems = getHistoryItems()
        val concludedItem = HistoryItem(
            service.providerName, 
            service.serviceName, 
            rating, 
            "Serviço concluído",
            service.paymentMethod,
            service.paymentCondition,
            service.location,
            service.serviceValue
        )
        historyItems.add(0, concludedItem)
        saveHistoryItems(historyItems)

        // Refresh UI
        loadScheduledServices()
        loadHistoryServices()
    }

    private fun showCancelConfirmationDialog(service: ScheduledService) {
        AlertDialog.Builder(requireContext())
            .setTitle("Cancelar Agendamento")
            .setMessage("Tem certeza que deseja cancelar este serviço?")
            .setPositiveButton("Sim") { _, _ ->
                cancelService(service)
            }
            .setNegativeButton("Não", null)
            .show()
    }

    private fun cancelService(serviceToCancel: ScheduledService) {
        val scheduledServices = getScheduledServices()
        scheduledServices.removeIf { it == serviceToCancel }
        saveScheduledServices(scheduledServices)

        val historyItems = getHistoryItems()
        val canceledItem = HistoryItem(serviceToCancel.providerName, serviceToCancel.serviceName, 0f, "Serviço cancelado")
        historyItems.add(0, canceledItem)
        saveHistoryItems(historyItems)

        loadScheduledServices()
        loadHistoryServices()
    }

    private fun getScheduledServices(): MutableList<ScheduledService> {
        val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val json = sharedPref.getString("scheduled_services", "[]")
        val type = object : TypeToken<MutableList<ScheduledService>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun saveScheduledServices(services: List<ScheduledService>) {
        val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("scheduled_services", gson.toJson(services))
            apply()
        }
    }

    private fun getHistoryItems(): MutableList<HistoryItem> {
        val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val json = sharedPref.getString("history_services", "[]")
        val type = object : TypeToken<MutableList<HistoryItem>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun saveHistoryItems(items: List<HistoryItem>) {
        val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("history_services", gson.toJson(items))
            apply()
        }
    }

    private fun showDetailsDialog(item: HistoryItem) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_service_details, null)
        builder.setView(dialogView)

        val dialog = builder.create()

        dialogView.findViewById<TextView>(R.id.detail_service_name).text = item.serviceDescription
        dialogView.findViewById<TextView>(R.id.detail_provider_name).text = item.providerName
        dialogView.findViewById<TextView>(R.id.detail_status_value).text = item.status
        dialogView.findViewById<TextView>(R.id.detail_payment_method).text = item.paymentMethod
        dialogView.findViewById<TextView>(R.id.detail_payment_condition).text = item.paymentCondition
        dialogView.findViewById<TextView>(R.id.detail_location).text = item.location
        dialogView.findViewById<TextView>(R.id.detail_value).text = item.serviceValue
        
        dialogView.findViewById<ImageButton>(R.id.close_button).setOnClickListener { dialog.dismiss() }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }
}