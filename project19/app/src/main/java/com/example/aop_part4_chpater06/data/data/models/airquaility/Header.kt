package com.example.aop_part4_chpater06.data.data.models.airquaility


import com.google.gson.annotations.SerializedName

data class Header(
    @SerializedName("resultCode")
    val resultCode: String?,
    @SerializedName("resultMsg")
    val resultMsg: String?
)