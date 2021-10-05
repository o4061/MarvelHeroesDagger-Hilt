package com.userfaltakas.marvelheroesdagger_hilt.repository

import com.userfaltakas.marvelheroesdagger_hilt.api.Resource
import com.userfaltakas.marvelheroesdagger_hilt.data.api.HeroesResponse
import com.userfaltakas.marvelheroesdagger_hilt.data.api.Result

class FakeHeroesRepositoryTest : HeroRepository {
    private val heroes = mutableListOf<Result>()
    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun getHeroes(offset: Int): Resource<HeroesResponse> {
        return if (shouldReturnNetworkError) {
            Resource.Error("Error", null)
        } else {
            Resource.Success(HeroesResponse(200, null, "OK"))
        }
    }

    override suspend fun addHero(result: Result): Long {
        heroes.add(result)
        return result.id.toLong()
    }

    override suspend fun getSquad(): List<Result> {
        return heroes
    }

    override suspend fun removeHero(result: Result) {
        heroes.remove(result)
    }

    override suspend fun isHeroExist(id: Int): Boolean {
        heroes.forEach {
            if (it.id == id) {
                return true
            }
        }
        return false
    }
}