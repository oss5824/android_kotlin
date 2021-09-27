package com.example.aop_part5_chapter05.di

import android.app.Activity
import com.example.aop_part5_chapter05.data.api.StationApi
import com.example.aop_part5_chapter05.data.api.StationStorageApi
import com.example.aop_part5_chapter05.data.db.AppDatabase
import com.example.aop_part5_chapter05.data.preference.PreferenceManager
import com.example.aop_part5_chapter05.data.preference.SharedPreferenceManager
import com.example.aop_part5_chapter05.data.repository.StationRepository
import com.example.aop_part5_chapter05.data.repository.StationRepositoryImpl
import com.example.aop_part5_chapter05.presentation.station.StationsContract
import com.example.aop_part5_chapter05.presentation.station.StationsFragment
import com.example.aop_part5_chapter05.presentation.station.StationsPresenter
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule=module{

    single{ Dispatchers.IO}

    single{AppDatabase.build(androidApplication())}
    single{get<AppDatabase>().stationDao()}

    single{androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE)}
    single<PreferenceManager>{SharedPreferenceManager(get())}

    single<StationApi>{StationStorageApi(Firebase.storage)}

    single<StationRepository>{StationRepositoryImpl(get(),get(),get(),get())}

    scope<StationsFragment>{
       scoped<StationsContract.Presenter>{StationsPresenter(getSource(),get())}
        //StationsContract.Presenter 는 StationsFragment 의 라이프 사이클이 끝나면 의미 없어짐
    }

}