package com.userfaltakas.marvelheroesdagger_hilt.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.userfaltakas.marvelheroesdagger_hilt.data.api.Result

@Database(
    entities = [Result::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class HeroDatabase : RoomDatabase() {
    abstract fun getHeroDao(): HeroDao
}
