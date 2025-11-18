package com.app.servu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment

class QuickScheduleDialogFragment : DialogFragment() {

    interface QuickScheduleListener {
        fun onQuickScheduleConfirmed(serviceName: String, date: String, time: String)
    }

    var listener: QuickScheduleListener? = null
    private var serviceName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        serviceName = arguments?.getString("serviceName")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_quick_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.quick_schedule_title).text = "Agendar ${serviceName}"
        val datePicker = view.findViewById<DatePicker>(R.id.date_picker)
        val timePicker = view.findViewById<TimePicker>(R.id.time_picker)
        val confirmButton = view.findViewById<Button>(R.id.confirm_quick_schedule_button)

        timePicker.setIs24HourView(true)

        confirmButton.setOnClickListener {
            val date = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"
            val time = "${timePicker.hour}:${String.format("%02d", timePicker.minute)}"
            listener?.onQuickScheduleConfirmed(serviceName!!, date, time)
            dismiss()
        }
    }

    companion object {
        fun newInstance(serviceName: String): QuickScheduleDialogFragment {
            val args = Bundle()
            args.putString("serviceName", serviceName)
            val fragment = QuickScheduleDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}