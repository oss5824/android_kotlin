package com.example.aop_part4_chpater06.data.data.services

import com.example.aop_part4_chpater06.BuildConfig
import com.example.aop_part4_chpater06.data.data.monitoringstation.MonitoringStation
import com.example.aop_part4_chpater06.data.data.monitoringstation.MonitoringStationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AirKoreaApiService {

    @GET("B552584/MsrstnInfoInqireSvc/getNearbyMsrstnList"
    +"?serviceKey=${BuildConfig.AIR_KOREA_SERVICE_KEY}"+
    "&returnType=json")
    suspend fun getNearbyMonitoringStation(
        @Query("tmX") tmX: Double,
        @Query("tmY") tmY: Double
    ):Response<MonitoringStationResponse>
}