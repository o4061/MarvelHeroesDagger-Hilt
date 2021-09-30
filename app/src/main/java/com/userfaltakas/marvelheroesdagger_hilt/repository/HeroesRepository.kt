package com.userfaltakas.marvelheroesdagger_hilt.repository

import com.userfaltakas.marvelheroesdagger_hilt.api.HeroesAPI
import com.userfaltakas.marvelheroesdagger_hilt.constant.Constants
import com.userfaltakas.marvelheroesdagger_hilt.data.api.HeroesResponse
import com.userfaltakas.marvelheroesdagger_hilt.data.api.Result
import com.userfaltakas.marvelheroesdagger_hilt.database.HeroDao
import retrofit2.Response
import javax.inject.Inject

class HeroesRepository @Inject constructor(
    val heroDao: HeroDao,
    val heroesApi: HeroesAPI
) {

    suspend fun getHeroes(offset: Int): Response<HeroesResponse> {
        val filter = mutableMapOf<String, String>()
        filter["ts"] = Constants.ts
        filter["apikey"] = Constants.API_KEY
        filter["hash"] = Constants.hash()
        filter["offset"] = offset.toString()
        return heroesApi.getHeroes(filter)
    }


    suspend fun addHero(result: Result) = heroDao.insert(result)
    suspend fun getSquad() = heroDao.getHeroes()
    suspend fun removeHero(result: Result) = heroDao.delete(result)
}