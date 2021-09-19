package com.example.aop_part5_chapter05.data.db

import androidx.room.*
import com.example.aop_part5_chapter05.data.db.entity.StationEntity
import com.example.aop_part5_chapter05.data.db.entity.StationSubwayCrossRefEntity
import com.example.aop_part5_chapter05.data.db.entity.StationWithSubwaysEntity
import com.example.aop_part5_chapter05.data.db.entity.SubwayEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDao {
    @Transaction
    @Query("SELECT * FROM StationEntity")
    fun getStationWithSubways(): Flow<List<StationWithSubwaysEntity>>

    //update와 같은 것들은 내부적으로 transaction 처리를 자동으로 해주기 때문에 query일 때만 위와 같이
    //Transaction을 붙여놓는 것을 알아두어야 함

    //Flow가 리턴이 되는데 one-shot같이 한번 호출해서 가져오면 끝이 아니라 observable하게
    //db가 변경될 때마다 받을 수 있게 room이 지원

  @Insert(onConflict= OnConflictStrategy.REPLACE)
    suspend fun insertStations(station: List<StationEntity>)
    //지하철 목록을 저장해야함

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    suspend fun insertSubways(subways: List<SubwayEntity>)
    //노선들을 저장해야함

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossReferences(reference: List<StationSubwayCrossRefEntity>)
    //역과 노선간의 상관관계를 저장해야함
}