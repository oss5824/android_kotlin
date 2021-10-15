package com.example.aop_part5_chpater01.viewmodel.todo

import com.example.aop_part5_chpater01.data.entity.ToDoEntity
import com.example.aop_part5_chpater01.domain.todo.InsertToDoListUseCase
import com.example.aop_part5_chpater01.presentation.list.ListViewModel
import com.example.aop_part5_chpater01.viewmodel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.koin.test.inject


/**
 * [ListViewModel] 을 테스트하기 위한 Unit Test Class
 * 1. initData()
 * 2. test viewModel fetch
 * 3. test Item Update
 * 4. test Item Delete All
 */

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
internal class ListViewModelTest : ViewModelTest() {
    private val viewModel: ListViewModel by inject()

    private val insertToDoListUseCase: InsertToDoListUseCase by inject()

    private val mockList = (0 until 10).map {
        ToDoEntity(
                id = it.toLong(),
                title = "title $it",
                description = "description $it",
                hasCompleted = false
        )
    }

    /**
     * 필요한 usercase들
     * 1. InsertToDoListUseCase
     * 2. GetToDoItemUseCase
     */
    @Before
    fun init() {
        initData()
    }

    private fun initData() = runBlockingTest {
        insertToDoListUseCase(mockList)
    }

    // Test: 입력된 데이터를 불러와서 검증
    @Test
    fun `test viewModel fetch`():Unit= runBlockingTest {
        val testObservable = viewModel.toDoListLiveData.test()
        viewModel.fetchData()
        testObservable.assertValueSequence(
                listOf(mockList)
        )
    }
}