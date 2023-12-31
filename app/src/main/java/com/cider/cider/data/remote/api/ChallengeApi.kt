package com.cider.cider.data.remote.api

import com.cider.cider.data.remote.model.RequestCertifyLike
import com.cider.cider.data.remote.model.RequestChallengeLike
import com.cider.cider.data.remote.model.ResponseCertifyItem
import com.cider.cider.data.remote.model.ResponseCertifyLike
import com.cider.cider.data.remote.model.ResponseChallengeItem
import com.cider.cider.data.remote.model.ResponseChallengeLike
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChallengeApi {

    @GET("/api/challenge/{filter}")
    suspend fun getChallengeList(
        @Path("filter") filter: String,
    ): Response<ArrayList<ResponseChallengeItem>>

    @GET("/api/certify/home")
    suspend fun getCertifyHome(
    ): Response<ArrayList<ResponseCertifyItem>>

    @GET("/api/challenge/home/{category}")
    suspend fun getChallengeCategory(
        @Path("category") category: String,
    ): Response<ArrayList<ResponseChallengeItem>>

    @GET("/api/challenge/home")
    suspend fun getChallengeHome(
        @Path("filter") filter: String,
    ): Response<ArrayList<ResponseChallengeItem>>

    @GET("/api/challenge/official/{filter}")
    suspend fun getChallengeOfficial(
        @Path("filter") filter: String,
    ): Response<ArrayList<ResponseChallengeItem>>

    @GET("/api/challenge/popular/{filter}")
    suspend fun getChallengePopular(
        @Path("filter") filter: String,
    ): Response<ArrayList<ResponseChallengeItem>>

    @POST("/api/certify/like/like")
    suspend fun postCertifyLike(
        @Body param: RequestCertifyLike
    ): Response<ResponseCertifyLike>

    @DELETE("/api/certify/like/like/{certifyId}")
    suspend fun deleteCertifyLike(
        @Path("certifyId") id: Int,
    ): Response<ResponseCertifyLike>

    @POST("/api/challenge/like")
    suspend fun postChallengeLike(
        @Body param: RequestChallengeLike
    ): Response<ResponseChallengeLike>

    @DELETE("/api/challenge/like/{challengeId}")
    suspend fun deleteChallengeLike(
        @Path("challengeId") id: Int,
    ): Response<ResponseChallengeLike>
}