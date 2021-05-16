package com.example.aop_part3_chapter04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aop_part3_chapter04.adapter.BookAdapter
import com.example.aop_part3_chapter04.api.BookService
import com.example.aop_part3_chapter04.databinding.ActivityMainBinding
import com.example.aop_part3_chapter04.model.BestSellerDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)


        initBookRecyclerView()

        val retrofit= Retrofit.Builder()
            .baseUrl("https://book.interpark.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val bookService=retrofit.create(BookService::class.java)

        bookService.getBestSellerBooks("6B9CC9647E6B4CA0F4277A4AD17427C009ED4E24FC8314DF12BB4A271F1BA1F6")
            .enqueue(object: Callback<BestSellerDto>{
                override fun onResponse(
                    call: Call<BestSellerDto>,
                    response: Response<BestSellerDto>
                ) {
                   //TODO 성공처리
                    if(response.isSuccessful.not()){
                        Log.e(TAG,"NOT!!SUCCESS")
                        return
                    }
                    response.body()?.let{
                        Log.d(TAG,it.toString())
                        it.books.forEach {book->
                            Log.d(TAG,book.toString())
                            }
                        adapter.submitList(it.books)
                    }

                }

                override fun onFailure(call: Call<BestSellerDto>, t: Throwable) {
                    //TODO 실패처리
                    Log.e(TAG,t.toString())
                }

            })

    }

    private fun initBookRecyclerView() {
        adapter = BookAdapter()
        binding.bookRecyclerView.layoutManager= LinearLayoutManager(this)
        binding.bookRecyclerView.adapter=adapter

    }

    companion object {
        private const val TAG = "MainActivity"
    }
}