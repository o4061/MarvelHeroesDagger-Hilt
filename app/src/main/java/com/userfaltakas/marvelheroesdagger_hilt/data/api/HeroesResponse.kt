package com.userfaltakas.marvelheroesdagger_hilt.data.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HeroesResponse(
    val code: Int? = null,
    val data: Data? = null,
    val status: String? = null
) : Parcelable