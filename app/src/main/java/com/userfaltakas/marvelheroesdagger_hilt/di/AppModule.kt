package com.userfaltakas.marvelheroesdagger_hilt.di

import android.content.Context
import androidx.room.Room
import com.userfaltakas.marvelheroesdagger_hilt.api.HeroesAPI
import com.userfaltakas.marvelheroesdagger_hilt.constant.Constants.BASE_URL
import com.userfaltakas.marvelheroesdagger_hilt.database.HeroDao
import com.userfaltakas.marvelheroesdagger_hilt.database.HeroDatabase
import com.userfaltakas.marvelheroesdagger_hilt.repository.HeroesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHeroesAPI(): HeroesAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(HeroesAPI::class.java)


    @Singleton
    @Provides
    fun provideHeroesDatabase(
        @ApplicationContext app: Context
    ): HeroDatabase = Room.databaseBuilder(
        app,
        HeroDatabase::class.java,
        "squad_db.db"
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db: HeroDatabase): HeroDao = db.getHeroDao()

    @Singleton
    @Provides
    fun provideHeroesRepository(heroDao: HeroDao, heroesAPI: HeroesAPI): HeroesRepository =
        HeroesRepository(heroDao, heroesAPI)
}