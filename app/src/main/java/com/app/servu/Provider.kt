package com.app.servu

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Provider(
    val id: String,
    val name: String,
    val specialty: String,
    val rating: Float,
    val description: String,
    val category: String, // Added category for searching
    val profileImageResId: Int,
    val backgroundImageResId: Int,
    val services: List<ProviderService>
) : Parcelable

@Parcelize
data class ProviderService(
    val name: String,
    val value: String,
    val time: String,
    val imageResId: Int
) : Parcelable
