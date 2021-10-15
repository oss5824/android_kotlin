package com.example.aop_part5_chpater01.data.repository

import com.example.aop_part5_chpater01.data.entity.ToDoEntity

interface ToDoRepository {
    suspend fun getToDoList():List<ToDoEntity>
    suspend fun insertToDoList(toDoList:List<ToDoEntity>)
}