package com.cider.cider.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cider.cider.R
import com.cider.cider.domain.model.CertifyModel
import com.cider.cider.domain.model.ChallengeModel
import com.cider.cider.domain.model.FeedModel
import com.cider.cider.domain.model.ImageCardModel
import com.cider.cider.domain.repository.ChallengeRepository
import com.cider.cider.domain.type.challenge.Category
import com.cider.cider.utils.getResourceUri
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeHomeViewModel @Inject constructor(
    private val repository: ChallengeRepository
):ViewModel() {

    private val _feed = MutableLiveData<List<FeedModel>>()
    val feed: LiveData<List<FeedModel>> get() = _feed

    private val _tabState = MutableLiveData<Category>()
    val tabState: LiveData<Category> get() = _tabState

    init {
        _tabState.value = Category.INVESTING
    }

    fun tabSelect(challenge: Category) {
        _tabState.value = challenge
    }


    fun changeFeedLike(targetId: Int) {
        val originalFeedList = _feed.value?: mutableListOf() // 이전 feed 리스트 가져오기

        val updatedFeedList = originalFeedList.map { feed ->
            if (feed.id == targetId) { // 변경하려는 FeedModel의 id를 확인하고 해당 객체를 변경
                Log.d("TEST feed click1","$feed")
                feed.copy(likeCheck = !feed.likeCheck) // 변경된 값을 포함한 객체 복사본 생성
            } else {
                Log.d("TEST feed click2","$feed")
                feed // 변경하지 않을 경우 기존 객체 반환
            }
        }
        _feed.value = updatedFeedList
    }
}