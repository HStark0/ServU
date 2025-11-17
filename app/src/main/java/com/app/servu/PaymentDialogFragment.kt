package com.app.servu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment

class PaymentDialogFragment : DialogFragment() {

    interface PaymentDialogListener {
        fun onPaymentConfirmed(paymentMethod: String, paymentCondition: String)
    }

    var listener: PaymentDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val paymentMethodGroup = view.findViewById<RadioGroup>(R.id.payment_method_group)
        val paymentConditionGroup = view.findViewById<RadioGroup>(R.id.payment_condition_group)
        val finishButton = view.findViewById<Button>(R.id.finish_schedule_button)

        finishButton.setOnClickListener {
            val selectedMethodId = paymentMethodGroup.checkedRadioButtonId
            val selectedConditionId = paymentConditionGroup.checkedRadioButtonId

            if (selectedMethodId != -1 && selectedConditionId != -1) {
                val paymentMethod = view.findViewById<RadioButton>(selectedMethodId).text.toString()
                val paymentCondition = view.findViewById<RadioButton>(selectedConditionId).text.toString()
                listener?.onPaymentConfirmed(paymentMethod, paymentCondition)
                dismiss()
            }
        }
    }
}