package com.example.aop_part04_chapter02.service

import retrofit2.Call
import retrofit2.http.GET

interface MusicService {
    @GET("/v3/dabac193-f20b-43e2-bb3c-22b641bfe75c")
    fun listMusics(): Call<MusicDto>
}