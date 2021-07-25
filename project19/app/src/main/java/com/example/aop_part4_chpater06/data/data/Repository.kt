package com.example.aop_part4_chpater06.data.data

import com.example.aop_part4_chpater06.BuildConfig
import com.example.aop_part4_chpater06.data.data.models.airquaility.MeasuredValue
import com.example.aop_part4_chpater06.data.data.models.monitoringstation.MonitoringStation
import com.example.aop_part4_chpater06.data.data.services.AirKoreaApiService
import com.example.aop_part4_chpater06.data.data.services.KakaoLocalApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Repository {

    suspend fun getNearbyMonitoringStation(latitude: Double, longitude: Double):MonitoringStation? {
        val tmCoordinates = kakaoLocalApiService.getTmCoordinates(longitude, latitude)
            .body()
            ?.documents
            ?.firstOrNull()
        val tmX=tmCoordinates?.x
        val tmY=tmCoordinates?.y

        return airKoreaApiService.getNearbyMonitoringStation(tmX!!,tmY!!)
            .body()
            ?.response
            ?.body
            ?.monitoringStations
            ?.minByOrNull { it.tm ?:Double.MAX_VALUE }
    }

    private val kakaoLocalApiService: KakaoLocalApiService by lazy {
        Retrofit.Builder().baseUrl(Url.KAKAO_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildHttpClient())
            .build()
            .create()
    }

    private val airKoreaApiService: AirKoreaApiService by lazy {
        Retrofit.Builder().baseUrl(Url.AIR_KOREA_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildHttpClient())
            .build()
            .create()
    }

    suspend fun getLatestAirQualityData(stationName: String): MeasuredValue? =
        airKoreaApiService
            .getRealtimeAirQualties(stationName)
            .body()
            ?.response
            ?.body
            ?.measuredValues
            ?.firstOrNull()

    private fun buildHttpClient(): OkHttpClient = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    ).build()
}