package com.example.aop_part4_chpater06.data.data.models.monitoringstation


import com.google.gson.annotations.SerializedName

data class MonitoringStationResponse(
    @SerializedName("response")
    val response: Response?
)