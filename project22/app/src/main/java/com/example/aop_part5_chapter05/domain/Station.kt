package com.example.aop_part5_chapter05.domain


data class Station(
    val name: String,
    val isFavorited: Boolean,
    val connectedSubways: List<Subway>
)