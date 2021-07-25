package com.example.aop_part4_chpater06.data.data.models.monitoringstation


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("body")
    val body: Body?,
    @SerializedName("header")
    val header: Header?
)