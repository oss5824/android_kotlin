package com.example.aop_part5_chapter05.presentation.station

import com.example.aop_part5_chapter05.data.repository.StationRepository
import com.example.aop_part5_chapter05.domain.Station
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StationsPresenter(
    private val view: StationsContract.View,
    private val stationRepository: StationRepository
) : StationsContract.Presenter {

    override val scope: CoroutineScope = MainScope()

    private val queryString: MutableStateFlow<String> = MutableStateFlow("")
    private val stations: MutableStateFlow<List<Station>> = MutableStateFlow(emptyList())

    init {
        observeStations()
    }

    override fun onViewCreated() {
        scope.launch {
            view.showStations(stations.value)
            stationRepository.refreshStations()
        }
    }

    override fun onDestroyView() {}

    override fun filterStations(query: String) {
        scope.launch {
            queryString.emit(query)
        }
    }

    private fun observeStations() {
        stationRepository
            .stations.combine(queryString) { stations, query ->
                if (query.isBlank()) {
                    stations
                    //비어있다는 것은 아무것도 검색하지 않았으므로 전체목록을 리턴
                } else {
                    stations.filter { it.name.contains(query) }
                    //이름에 따라서 필터링을 해줌
                    //ex '구'라는 단어만 입력해도 구리 구로 등등 구라는 이름이 들어간 역들이 나옴
                }
            }
            .onStart { view.showLoadingIndicator() }// stations이라는 flow를 처음 구독을 시작햇을 때
            .onEach {
                // onEach는 각각의 item이 emit될 때마다 로직을 처리해줌
                if (it.isNotEmpty()) {
                    view.hideLoadingIndicator()
                }
                stations.value = it
                view.showStations(it)
            }
            .catch {
                it.printStackTrace()
                view.hideLoadingIndicator()
            }
            .launchIn(scope)//해당 scope안에서 launch하고 있따.
    }

    /*
    override fun toggleStationFavorite(station: Station) {
        scope.launch {
            stationRepository.updateStation(station.copy(isFavorited = !station.isFavorited))
        }
    }

     */
}