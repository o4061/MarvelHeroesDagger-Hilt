package com.userfaltakas.marvelheroesdagger_hilt.ui.fragment.heroesPreview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.userfaltakas.marvelheroesdagger_hilt.data.api.Result
import com.userfaltakas.marvelheroesdagger_hilt.repository.HeroesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroPreviewViewModel @Inject constructor(
    private val heroesRepository: HeroesRepository
) : ViewModel() {

    lateinit var heroExist: MutableLiveData<Boolean>

    fun addHeroToSquad(result: Result) = viewModelScope.launch {
        heroesRepository.addHero(result)
    }

    fun removeHeroFromSquad(result: Result) = viewModelScope.launch {
        heroesRepository.removeHero(result)
    }

    fun isHeroExist(id: Int) = viewModelScope.launch {
        heroExist = MutableLiveData()
        heroExist.postValue(heroesRepository.isHeroExist(id))
    }
}