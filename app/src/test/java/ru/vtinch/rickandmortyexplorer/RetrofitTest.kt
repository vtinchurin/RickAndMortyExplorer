package ru.vtinch.rickandmortyexplorer

import android.util.Log
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.vtinch.rickandmortyexplorer.core.Core
import ru.vtinch.rickandmortyexplorer.search.data.cloud.CloudDataSource

class RetrofitTest {

    private lateinit var cloudDataSource: CloudDataSource

    @Before
    fun setup() {
        val service = Core().cloudModule.service()
        cloudDataSource = CloudDataSource.Base(
            service,
        )
    }

    @Test
    fun test_load(): Unit = runBlocking {
        try {
            val dataList = cloudDataSource.fetch("")
            assertEquals(20, dataList.size)
        }catch (e:Exception){
            println()
        }
    }
}