package com.userfaltakas.marvelheroesdagger_hilt.repository

import com.userfaltakas.marvelheroesdagger_hilt.api.Resource
import com.userfaltakas.marvelheroesdagger_hilt.data.api.HeroesResponse
import com.userfaltakas.marvelheroesdagger_hilt.data.api.Result

interface HeroRepository {
    suspend fun getHeroes(offset: Int): Resource<HeroesResponse>
    suspend fun addHero(result: Result): Long
    suspend fun getSquad(): List<Result>
    suspend fun removeHero(result: Result)
    suspend fun isHeroExist(id: Int): Boolean
}