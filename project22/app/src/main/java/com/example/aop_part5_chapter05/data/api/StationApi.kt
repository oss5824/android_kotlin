package com.example.aop_part5_chapter05.data.api

import com.example.aop_part5_chapter05.data.db.entity.StationEntity
import com.example.aop_part5_chapter05.data.db.entity.SubwayEntity

interface StationApi {

    suspend fun getStationDataUpdatedTimeMills():Long
    //언제 업데이트 됐는지
    suspend fun getStationSubways():List<Pair<StationEntity, SubwayEntity>>
    //서로의 쌍
}