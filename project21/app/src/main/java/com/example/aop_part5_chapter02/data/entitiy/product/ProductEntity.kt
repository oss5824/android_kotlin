package com.example.aop_part5_chapter02.data.entitiy.product



import java.util.*

//@Entity
data class ProductEntity(
    val id: Long,
    val createdAt: Date,
    val productName: String,
    val productPrice: Int,
    val productImage: String,
    val productType: String,
    val productIntroductionImage: String
)