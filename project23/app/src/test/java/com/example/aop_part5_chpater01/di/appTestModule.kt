package com.example.aop_part5_chpater01.di

import com.example.aop_part5_chpater01.data.repository.TestToDoRepository
import com.example.aop_part5_chpater01.data.repository.ToDoRepository
import com.example.aop_part5_chpater01.domain.todo.GetToDoListUseCase
import com.example.aop_part5_chpater01.domain.todo.InsertToDoListUseCase
import com.example.aop_part5_chpater01.presentation.list.ListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    viewModel{ListViewModel(get())}
    // UseCase
    factory{GetToDoListUseCase(get())}
    factory{InsertToDoListUseCase(get())}

    // Repository
    single<ToDoRepository>{ TestToDoRepository() }
}