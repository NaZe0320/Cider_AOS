package com.cider.cider.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cider.cider.domain.model.ChallengeCardModel
import com.cider.cider.domain.repository.ChallengeRepository
import com.cider.cider.domain.type.Filter
import com.cider.cider.domain.type.challenge.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeListViewModel @Inject constructor(
    private val repository: ChallengeRepository
): ViewModel() {
    private val _challenge = MutableLiveData<List<ChallengeCardModel>>()
    val challenge: LiveData<List<ChallengeCardModel>> get() = _challenge

    fun getChallenge(filter: Filter) {
        viewModelScope.launch {
            _challenge.value = repository.getChallengeList(filter = filter)
        }
    }

    fun getChallengePopular(filter: Filter) {
        viewModelScope.launch {
            _challenge.value = repository.getChallengePopular(filter = filter)
        }
    }

    fun getChallengeOfficial(filter: Filter) {
        viewModelScope.launch {
            _challenge.value = repository.getChallengeOfficial(filter = filter)
        }
    }

    fun getChallengeCategory(challenge: Category) {
        viewModelScope.launch {
            _challenge.value = repository.getChallengeCategory(challenge)
        }
    }

    fun changeLike(targetId: Int) {
        val beforeList = _challenge.value?: mutableListOf()
        viewModelScope.launch {
            beforeList.map {
                if (it.id == targetId) {

                    Log.d("TEST API", "Before repo ${it.like}")

                    if (it.like) { //true 였다면 false 로 변경
                        if (repository.postChallengeLike(targetId))
                            it
                        else
                            it.copy(like = true)
                    } else { //false 였다면 true 로 변경
                        if (repository.deleteChallengeLike(targetId))
                            it
                        else
                            it.copy(like = false)
                    }
                } else {
                    it
                }
            }
        }
    }
}