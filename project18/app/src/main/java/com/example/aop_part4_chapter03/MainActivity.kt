package com.example.aop_part4_chapter03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.aop_part4_chapter03.databinding.ActivityMainBinding
import com.example.aop_part4_chapter03.model.LocationLatLngEntity
import com.example.aop_part4_chapter03.model.SearchResultEntity
import com.example.aop_part4_chapter03.response.search.Poi
import com.example.aop_part4_chapter03.response.search.Pois
import com.example.aop_part4_chapter03.utility.RetrofitUtil
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        job = Job()

        initAdapter()
        initViews()
        bindViews()
        initData()
    }

    private fun bindViews() = with(binding) {

        searchButton.setOnClickListener {
            searchKeyword(searchBarInputView.text.toString())
        }
    }

    private fun initAdapter() {
        adapter = SearchRecyclerAdapter()
    }

    private fun initViews() = with(binding) {
        emptyResultTextView.isVisible = false
        recyclerView.adapter = adapter
    }

    private fun initData() {
        adapter.notifyDataSetChanged()
    }

    private fun setData(pois: Pois) {
        val dataList = pois.poi.map {
            SearchResultEntity(
                name = it.name ?: "빌딜명 없음",
                fullAddress = makeMainAdress(it),
                locationLatLng = LocationLatLngEntity(it.noorLat, it.noorLon)
            )
        }
        adapter.setSearchResultList(dataList) {
            Toast.makeText(
                this,
                "빌딩이름 : ${it.name}, 빌딩주소: ${it.fullAddress}, 위도/경도:${it.locationLatLng}",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun makeMainAdress(poi: Poi): String =
        if (poi.secondNo?.trim().isNullOrEmpty()) {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    poi.firstNo?.trim()
        } else {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    (poi.firstNo?.trim() ?: "") + " " +
                    poi.secondNo?.trim()
        }

    private fun searchKeyword(keywordString: String) {
        launch(coroutineContext) {
            try {
                withContext(Dispatchers.IO) {
                    val response = RetrofitUtil.apiService.getSearchLocation(
                        keyword = keywordString
                    )
                    if (response.isSuccessful) {
                        val body = response.body()
                        withContext(Dispatchers.Main) {
                            Log.e("response", body.toString())
                            body?.let { searchResponse ->
                                setData(searchResponse.searchPoiInfo.pois)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity, "검색하는 과정에서 에러가 발생했습니다. : ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}