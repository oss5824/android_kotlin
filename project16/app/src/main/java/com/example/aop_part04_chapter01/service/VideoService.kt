package com.example.aop_part04_chapter01.service

import com.example.aop_part04_chapter01.dto.VideoDto
import retrofit2.Call
import retrofit2.http.GET

interface VideoService {
    @GET("/v3/4507f8fb-3024-41d7-b4a0-51eeb6c86873")
    fun listVideos(): Call<VideoDto>
}