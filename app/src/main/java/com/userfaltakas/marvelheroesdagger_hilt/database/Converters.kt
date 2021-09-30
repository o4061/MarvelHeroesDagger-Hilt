package com.userfaltakas.marvelheroesdagger_hilt.database

import androidx.room.TypeConverter
import com.userfaltakas.marvelheroesdagger_hilt.data.api.Thumbnail

class Converters {
    @TypeConverter
    fun fromThumbnail(thumbnail: Thumbnail): String {
        return "${thumbnail.path}.${thumbnail.extension}"
    }

    @TypeConverter
    fun toThumbnail(name: String): Thumbnail {
        return Thumbnail(name, name)
    }
}
