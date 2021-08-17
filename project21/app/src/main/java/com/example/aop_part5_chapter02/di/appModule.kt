package com.example.aop_part5_chapter02.di

import com.example.aop_part5_chapter02.data.network.buildOkHttpClient
import com.example.aop_part5_chapter02.data.network.provideGsonConverterFactory
import com.example.aop_part5_chapter02.data.network.provideProductApiService
import com.example.aop_part5_chapter02.data.network.provideProductRetrofit
import com.example.aop_part5_chapter02.data.repository.DefaultProductRepository
import com.example.aop_part5_chapter02.data.repository.ProductRepository
import com.example.aop_part5_chapter02.domain.GetProductItemUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val appModule = module{


    single { Dispatchers.Main }
    single { Dispatchers.IO }

    factory { GetProductItemUseCase(get()) }

    single<ProductRepository> { DefaultProductRepository(get(), get()) }

    single { provideGsonConverterFactory() }

    single { buildOkHttpClient() }

    single { provideProductRetrofit(get(), get()) }

    single { provideProductApiService(get()) }

}