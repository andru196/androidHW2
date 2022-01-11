package com.example.poke.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    val color: Int,
    val height: Double,
    val weight: Double,
    val is_default: Boolean,
    val baseExp: Int
) : Parcelable