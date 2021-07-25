package com.example.aop_part4_chpater06.data.data.services

import com.example.aop_part4_chpater06.BuildConfig
import com.example.aop_part4_chpater06.data.data.models.tmcoordinates.TmCoordinatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoLocalApiService {


    @Headers("Authorization: KakaoAK ${BuildConfig.KAKAO_API_KEY}")
    @GET("/v2/local/search/address.json?output_coord=TM")
    suspend fun  getTmCoordinates(
        @Query("x") longitude: Double,
        @Query("y") latitude: Double
    ): Response<TmCoordinatesResponse>
}