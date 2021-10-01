package com.userfaltakas.marvelheroesdagger_hilt.data.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Thumbnail(
    val extension: String? = null,
    val path: String? = null
) : Parcelable {
    fun getURL(): String {
        return if (this.extension == this.path) {
            "${this.path}"
        } else {
            "${this.path}.${this.extension}"
        }
    }
}