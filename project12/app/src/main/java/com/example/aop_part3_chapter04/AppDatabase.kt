package com.example.aop_part3_chapter04

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aop_part3_chapter04.dao.HistoryDao
import com.example.aop_part3_chapter04.dao.ReviewDao
import com.example.aop_part3_chapter04.model.History
import com.example.aop_part3_chapter04.model.Review

@Database(entities=[History::class, Review::class],version=1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun reviewDao(): ReviewDao
}