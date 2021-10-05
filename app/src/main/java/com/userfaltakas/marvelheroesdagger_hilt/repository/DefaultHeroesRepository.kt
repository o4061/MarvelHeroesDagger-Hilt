package com.userfaltakas.marvelheroesdagger_hilt.repository

import com.userfaltakas.marvelheroesdagger_hilt.api.HeroesAPI
import com.userfaltakas.marvelheroesdagger_hilt.api.Resource
import com.userfaltakas.marvelheroesdagger_hilt.constant.Constants
import com.userfaltakas.marvelheroesdagger_hilt.data.api.HeroesResponse
import com.userfaltakas.marvelheroesdagger_hilt.data.api.Result
import com.userfaltakas.marvelheroesdagger_hilt.database.HeroDao
import javax.inject.Inject

class DefaultHeroesRepository @Inject constructor(
    val heroDao: HeroDao,
    private val heroesApi: HeroesAPI
) : HeroRepository {
    override suspend fun getHeroes(offset: Int): Resource<HeroesResponse> {
        val filter = mutableMapOf<String, String>()
        filter["ts"] = Constants.ts
        filter["apikey"] = Constants.API_KEY
        filter["hash"] = Constants.hash()
        filter["offset"] = offset.toString()
        val response = heroesApi.getHeroes(filter)
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun addHero(result: Result) = heroDao.insert(result)
    override suspend fun getSquad() = heroDao.getHeroes()
    override suspend fun removeHero(result: Result) = heroDao.delete(result)
    override suspend fun isHeroExist(id: Int) = heroDao.isHeroExist(id)
}