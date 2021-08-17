package com.example.aop_part5_chapter02.data.repository

import com.example.aop_part5_chapter02.data.entitiy.product.ProductEntity
import com.example.aop_part5_chapter02.data.network.ProductApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultProductRepository(
    private val productApi: ProductApiService,
    private val ioDispatcher: CoroutineDispatcher
):ProductRepository{
    override suspend fun getProductList(): List<ProductEntity> = withContext(ioDispatcher){
        TODO("NOT")
    }

    override suspend fun getLocalProductList(): List<ProductEntity> = withContext(ioDispatcher){
        TODO("NOT")
    }

    override suspend fun insertProductItem(ProductItem: ProductEntity): Long = withContext(ioDispatcher){
        TODO("NOT")
    }

    override suspend fun insertProductList(ProductList: List<ProductEntity>) = withContext(ioDispatcher){
        TODO("NOT")
    }

    override suspend fun updateProductItem(ProductItem: ProductEntity) = withContext(ioDispatcher){
        TODO("NOT")
    }

    override suspend fun getProductItem(itemId: Long): ProductEntity? = withContext(ioDispatcher){
        TODO("NOT")
    }

    override suspend fun deleteAll() = withContext(ioDispatcher){
        TODO("NOT")
    }

    override suspend fun deleteProductItem(id: Long) = withContext(ioDispatcher){
        TODO("NOT")
    }

}