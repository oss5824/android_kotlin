package com.example.aop_part2_chpater04

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aop_part2_chpater04.dao.HistoryDao
import com.example.aop_part2_chpater04.model.History


@Database(entities=[History::class],version=1)
abstract  class AppDatabase:RoomDatabase(){
    abstract  fun historyDao():HistoryDao

}