package com.app.servu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment

class ScheduleDialogFragment : DialogFragment() {

    interface ScheduleDialogListener {
        fun onDateAndLocationSet(date: String, time: String, location: String)
    }

    var listener: ScheduleDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val datePicker = view.findViewById<DatePicker>(R.id.date_picker)
        val timePicker = view.findViewById<TimePicker>(R.id.time_picker)
        val locationEditText = view.findViewById<EditText>(R.id.location_edit_text)
        val continueButton = view.findViewById<Button>(R.id.continue_button)

        timePicker.setIs24HourView(true)

        continueButton.setOnClickListener {
            val date = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"
            val time = "${timePicker.hour}:${String.format("%02d", timePicker.minute)}" // Format minute
            val location = locationEditText.text.toString()
            if (location.isNotBlank()) {
                listener?.onDateAndLocationSet(date, time, location)
                dismiss()
            }
        }
    }
}