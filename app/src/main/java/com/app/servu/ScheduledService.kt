package com.app.servu

data class ScheduledService(
    val serviceName: String,
    val providerName: String,
    val date: String,
    val time: String, // Re-added the time field
    val location: String,
    val paymentMethod: String,
    val paymentCondition: String,
    val serviceValue: String
)