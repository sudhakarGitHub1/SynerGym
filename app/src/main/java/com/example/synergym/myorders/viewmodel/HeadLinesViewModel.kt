package com.example.synergym.myorders.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.synergym.myorders.data.NewsData
import com.example.synergym.myorders.database.NewsDataBase
import com.example.synergym.myorders.repository.HeadLinesRepository
import com.example.synergym.myorders.ui.HeadLineListner
import com.example.synergym.utils.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class HeadLinesViewModel(private val headLinesRepository: HeadLinesRepository) : ViewModel() {
    var headLineListner: HeadLineListner? = null
    private var noteDatabase: NewsDataBase? = null
    fun myNewsList(context: Context) {
        noteDatabase =
            Room.databaseBuilder(context, NewsDataBase::class.java, "data_base").build()
        headLineListner?.onStarted()
        Coroutines.main {
            try {
                    val newsResponse = headLinesRepository.myNews()
                    headLineListner?.onSuccess(newsResponse)
                    insertData(newsResponse)
            } catch (e: ApiException) {
                headLineListner?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                headLineListner?.onFailure(e.message!!)
            } catch (e: TimeoutException) {
                headLineListner?.onFailure(e.message!!)
            } catch (e: SocketExceptions) {
                headLineListner?.onFailure(e.message!!)
            }
        }
    }

    fun getDataFromDb(context: Context): NewsData {
        var dbDate:NewsData? = null
            GlobalScope.launch {
                getData()
            }
        return dbDate!!
    }

    fun getData() {
        noteDatabase!!.userDao()!!.fetchAllTasks();
    }

    private fun insertData(newsResponse: NewsData) {
        Coroutines.io {
            noteDatabase!!.userDao()!!.insertTask(newsResponse);
        }
    }

}