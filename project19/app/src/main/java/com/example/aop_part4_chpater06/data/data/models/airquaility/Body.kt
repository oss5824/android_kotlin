package com.example.aop_part4_chpater06.data.data.models.airquaility


import com.google.gson.annotations.SerializedName

data class Body(
    @SerializedName("items")
    val measuredValues: List<MeasuredValue>?,
    @SerializedName("numOfRows")
    val numOfRows: Int?,
    @SerializedName("pageNo")
    val pageNo: Int?,
    @SerializedName("totalCount")
    val totalCount: Int?
)