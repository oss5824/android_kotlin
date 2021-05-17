package com.example.aop_part3_chapter04

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.aop_part3_chapter04.adapter.BookAdapter
import com.example.aop_part3_chapter04.adapter.HistoryAdapter
import com.example.aop_part3_chapter04.api.BookService
import com.example.aop_part3_chapter04.databinding.ActivityMainBinding
import com.example.aop_part3_chapter04.model.BestSellerDto
import com.example.aop_part3_chapter04.model.Book
import com.example.aop_part3_chapter04.model.History
import com.example.aop_part3_chapter04.model.SearchBookDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BookAdapter
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var bookService: BookService
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initBookRecyclerView()
        initHistoryRecyclerView()

        db = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java,"BookSearchDB"
        ).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://book.interpark.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        bookService = retrofit.create(BookService::class.java)

        bookService.getBestSellerBooks(getString(R.string.interparkAPIKey))
            .enqueue(object : Callback<BestSellerDto> {
                override fun onResponse(
                    call: Call<BestSellerDto>,
                    response: Response<BestSellerDto>
                ) {
                    //TODO 성공처리
                    if (response.isSuccessful.not()) {
                        Log.e(TAG, "NOT!!SUCCESS")
                        return
                    }
                    response.body()?.let {
                        Log.d(TAG, it.toString())
                        it.books.forEach { book ->
                            Log.d(TAG, book.toString())
                        }
                        adapter.submitList(it.books)
                    }

                }

                override fun onFailure(call: Call<BestSellerDto>, t: Throwable) {
                    //TODO 실패처리
                    Log.e(TAG, t.toString())
                }

            })

    }

    private fun search(keyword: String) {
        bookService.getBooksByName(getString(R.string.interparkAPIKey), keyword)
            .enqueue(object : Callback<SearchBookDto> {
                override fun onResponse(
                    call: Call<SearchBookDto>,
                    response: Response<SearchBookDto>
                ) {
                    //TODO 성공처리
                    hideHistoryView()
                    saveSearchKeyword(keyword)

                    if (response.isSuccessful.not()) {
                        Log.e(TAG, "NOT!!SUCCESS")
                        return
                    }
                    adapter.submitList(response.body()?.books.orEmpty())

                }

                override fun onFailure(call: Call<SearchBookDto>, t: Throwable) {
                    //TODO 실패처리
                    hideHistoryView()
                    Log.e(TAG, t.toString())
                }

            })
    }

    private fun initBookRecyclerView() {
        adapter = BookAdapter(itemClickedListener={
            val intent= Intent(this,DetailActivity::class.java)
            intent.putExtra("bookModel",it)
            //TODO 클래스를 직렬화시켜서 클래스 자체를 넘겨주는 방식이라고 한다.
            startActivity(intent)
        })
        binding.bookRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookRecyclerView.adapter = adapter

    }
    private fun initHistoryRecyclerView(){
        historyAdapter=HistoryAdapter(historyDeleteClickedListener = {
            deleteSearchKeyword(it)
        })
        binding.historyRecyclerView.layoutManager=LinearLayoutManager(this)
        binding.historyRecyclerView.adapter=historyAdapter

        initSearchEditText()
    }
    private fun initSearchEditText(){
        binding.searchEditText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN) {
                search(binding.searchEditText.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        binding.searchEditText.setOnTouchListener { v, event ->
            if(event.action==MotionEvent.ACTION_DOWN){
                showHistoryView()
            }
            return@setOnTouchListener false
        }
    }
    private fun showHistoryView(){
        Thread{
            val keywords=db.historyDao().getAll().reversed()
            runOnUiThread{
                binding.historyRecyclerView.isVisible = true
                historyAdapter.submitList(keywords.orEmpty())
            }
        }.start()
        binding.historyRecyclerView.isVisible = true
    }
    private fun hideHistoryView(){
        binding.historyRecyclerView.isVisible = false
    }
    private fun saveSearchKeyword(keyword: String){
        Thread{
            db.historyDao().insertHistory(History(null,keyword))
        }.start()
    }
    private fun deleteSearchKeyword(keyword: String){
        Thread{
            db.historyDao().delete(keyword)
            showHistoryView()
        }.start()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}