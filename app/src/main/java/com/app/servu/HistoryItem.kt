package com.app.servu

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryItem(
    val providerName: String,
    val serviceDescription: String,
    val rating: Float,
    val status: String,
    val paymentMethod: String? = null,
    val paymentCondition: String? = null,
    val location: String? = null,
    val serviceValue: String? = null
) : Parcelable