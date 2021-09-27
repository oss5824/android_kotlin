package com.example.aop_part5_chapter05.data.repository

import android.preference.Preference
import com.example.aop_part5_chapter05.data.api.StationApi
import com.example.aop_part5_chapter05.data.db.StationDao
import com.example.aop_part5_chapter05.data.db.entity.StationSubwayCrossRefEntity
import com.example.aop_part5_chapter05.data.db.entity.mapper.toStations
import com.example.aop_part5_chapter05.data.preference.PreferenceManager
import com.example.aop_part5_chapter05.domain.ArrivalInformation
import com.example.aop_part5_chapter05.domain.Station
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class StationRepositoryImpl(
    private val stationApi: StationApi,
    private val stationDao: StationDao,
    private val preferenceManager: PreferenceManager,
    private val dispatcher: CoroutineDispatcher
) : StationRepository {

    override val stations: Flow<List<Station>> =
        stationDao.getStationWithSubways()
            .distinctUntilChanged()
                //TODO 다른 것으로 인해 Change가 매번 되는 것을 방지해주기 위해 원하는 것이 변경되었을 때만 change되게
            .map { stations -> stations.toStations().sortedByDescending { it.isFavorited } }
            .flowOn(dispatcher)

    override suspend fun refreshStations() = withContext(dispatcher) {
        val fileUpdatedTimeMillis = stationApi.getStationDataUpdatedTimeMills()
        val lastDatabaseUpdatedTimeMillis =
            preferenceManager.getLong(KEY_LAST_DATABASE_UPDATED_TIME_MILLIS)

        if (lastDatabaseUpdatedTimeMillis == null || fileUpdatedTimeMillis > lastDatabaseUpdatedTimeMillis) {
            stationDao.insertStationSubways(stationApi.getStationSubways())
            preferenceManager.putLong(KEY_LAST_DATABASE_UPDATED_TIME_MILLIS,fileUpdatedTimeMillis)
        }
    }

    companion object {
        private const val KEY_LAST_DATABASE_UPDATED_TIME_MILLIS =
            "KEY_LAST_DATABASE_UPDATED_TIME_MILLIS"
    }
}