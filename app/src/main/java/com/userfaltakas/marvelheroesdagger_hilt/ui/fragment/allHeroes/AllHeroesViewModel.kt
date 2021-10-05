package com.userfaltakas.marvelheroesdagger_hilt.ui.fragment.allHeroes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.userfaltakas.marvelheroesdagger_hilt.api.Resource
import com.userfaltakas.marvelheroesdagger_hilt.constant.Constants
import com.userfaltakas.marvelheroesdagger_hilt.data.api.HeroesResponse
import com.userfaltakas.marvelheroesdagger_hilt.data.api.Result
import com.userfaltakas.marvelheroesdagger_hilt.repository.HeroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllHeroesViewModel @Inject constructor(
    private val heroesRepository: HeroRepository
) : ViewModel() {

    val heroes: MutableLiveData<Resource<HeroesResponse>> = MutableLiveData()
    private var heroesResponse: HeroesResponse? = null
    private var offset = 0
    private var total = 0
    val squad: MutableLiveData<List<Result>> = MutableLiveData()

    fun getHeroes() = viewModelScope.launch {
        heroes.postValue(Resource.Loading())
        val response = heroesRepository.getHeroes(offset)
        heroes.postValue(handleHeroPaging(response))
    }

    private fun handleHeroPaging(response: Resource<HeroesResponse>): Resource<HeroesResponse> {
        when (response) {
            is Resource.Success -> {
                offset += Constants.PAGE_OFFSET
                if (heroesResponse == null) {
                    heroesResponse = response.data
                    total = response.data?.data?.total!!
                } else {
                    val oldHeroes = heroesResponse!!.data?.results
                    val newHeroes = response.data?.data?.results
                    if (newHeroes != null) {
                        oldHeroes?.addAll(newHeroes)
                    }
                }
                return Resource.Success(heroesResponse!!)
            }
            else ->
                return response
        }
    }

    fun isLastPage(): Boolean {
        return offset >= total
    }

    fun getSquad() = viewModelScope.launch {
        squad.postValue(heroesRepository.getSquad())
    }
}