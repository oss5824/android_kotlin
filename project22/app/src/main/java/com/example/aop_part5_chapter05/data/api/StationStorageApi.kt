package com.example.aop_part5_chapter05.data.api

import com.example.aop_part5_chapter05.data.db.entity.StationEntity
import com.example.aop_part5_chapter05.data.db.entity.SubwayEntity
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class StationStorageApi(firebaseStorage: FirebaseStorage) : StationApi {

    private val sheetReference = firebaseStorage.reference.child(STATION_DATA_FILE_NAME)
    override suspend fun getStationDataUpdatedTimeMills(): Long =
        sheetReference.metadata.await().updatedTimeMillis
    //await로 비동기 처리가 수월


    override suspend fun getStationSubways(): List<Pair<StationEntity, SubwayEntity>> {
        val downloadSizeBytes = sheetReference.metadata.await().sizeBytes
        val byteArray = sheetReference.getBytes(downloadSizeBytes).await()

        //lines별로 파싱하고 drop을 통해 제일 윗줄을 날리고 csv의 파일형식상 A,B,C 이런식으로 데이터가
        //구성되어있기 때문에 ,를 기준으로 split
        return byteArray.decodeToString().lines().drop(1).map { it.split(",") }
            .map { StationEntity(it[1]) to SubwayEntity(it[0].toInt()) }
    }

    companion object {
        private const val STATION_DATA_FILE_NAME = "station_data.csv"
    }
}