package com.example.aop_part3_chapter07

import retrofit2.Call
import retrofit2.http.GET

interface HouseService {
    @GET("/v3/344ab7f3-2fc4-451b-98a0-7898951e6013")
    fun getHouseList(): Call<HouseDto>
}