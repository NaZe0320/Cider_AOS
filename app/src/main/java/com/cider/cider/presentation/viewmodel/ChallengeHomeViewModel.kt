package com.cider.cider.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cider.cider.R
import com.cider.cider.domain.model.ChallengeCardModel
import com.cider.cider.domain.model.ChallengeModel
import com.cider.cider.domain.model.FeedModel
import com.cider.cider.domain.model.ImageCardModel
import com.cider.cider.domain.type.challenge.Challenge
import com.cider.cider.domain.type.challenge.ParticipationStatus
import com.cider.cider.utils.getResourceUri
import javax.inject.Inject

class ChallengeHomeViewModel @Inject constructor(

):ViewModel() {

    private val _feed = MutableLiveData<List<FeedModel>>()
    val feed: LiveData<List<FeedModel>> get() = _feed

    private val _tabState = MutableLiveData<Challenge>()
    val tabState: LiveData<Challenge> get() = _tabState

    init {
        _tabState.value = Challenge.INVESTING
    }

    fun tabSelect(challenge: Challenge) {
        _tabState.value = challenge
    }

    fun testFeed(context: Context) {
        val list: MutableList<FeedModel> = mutableListOf()
        val image: MutableList<ImageCardModel> = mutableListOf()

        image.add(ImageCardModel(R.drawable.image_saving.getResourceUri(context)))
        image.add(ImageCardModel(R.drawable.image_saving.getResourceUri(context)))
        image.add(ImageCardModel(R.drawable.image_saving.getResourceUri(context)))
        image.add(ImageCardModel(R.drawable.image_saving.getResourceUri(context)))


        val image2: MutableList<ImageCardModel> = mutableListOf()

        image2.add(ImageCardModel(R.drawable.image_investing.getResourceUri(context)))
        image2.add(ImageCardModel(R.drawable.image_financial_learning.getResourceUri(context)))
        image2.add(ImageCardModel(R.drawable.image_saving.getResourceUri(context)))
        image2.add(ImageCardModel(R.drawable.image_money_management.getResourceUri(context)))


        list.add(
            FeedModel(
                id = 1, profile = null, nickname = "오늘챌린지팅", lv = 1,
                date = "23.05.15 14:45", title = "오늘 챌린지 인증하는데", content = "하루 만보 걷기 챌린지는 쉽고 재미있게 만보를 걸을 수있는 챌린지로, 제가 맨날 일하다가 한번 입원하고 나서 심각성을 느끼고 만들게 된 멋진 챌린지",
                imageList = image, challengeModel = ChallengeModel(id = 1, challengeType = Challenge.SAVING, title = "소비습관 하루에 열심히 해 화이팅 챌린지", people = 86), like = 346, likeCheck = false)
        )
        list.add(
            FeedModel(
                id = 2, profile = null, nickname = "오늘챌린지화팅", lv = 2,
                date = "23.05.15 14:45", title = "오늘 챌린지 인증하는데", content = "하루 만보 걷기 챌린지는 쉽고 재미있게 만보를 걸을 수있는 챌린지로, 제가 맨날 일하다가 한번 입원하고 나서 심각성을 느끼고 만들게 된 멋진 챌린지",
                imageList = image2, challengeModel = ChallengeModel(id = 1, challengeType = Challenge.INVESTING, title = "소비습관 하루에 열심히 해 화이팅 챌린지", people = 86), like = 36, likeCheck = false)
        )
        list.add(
            FeedModel(
                id = 3, profile = null, nickname = "오늘챌린지화이팅", lv = 3,
                date = "23.05.15 14:45", title = "오늘 챌린지 인증하는데", content = "하루 만보 걷기 챌린지는 쉽고 재미있게 만보를 걸을 수있는 챌린지로, 제가 맨날 일하다가 한번 입원하고 나서 심각성을 느끼고 만들게 된 멋진 챌린지",
                imageList = null, challengeModel = ChallengeModel(id = 1, challengeType = Challenge.MONEY_MANAGEMENT, title = "소비습관 하루에 열심히 해 화이팅 챌린지", people = 86), like = 46, likeCheck = false)
        )
        _feed.value = list
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