package com.example.synergym.myorders.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.synergym.myorders.data.NewsData
import com.example.synergym.myorders.database.NewsDataBase
import com.example.synergym.myorders.repository.HeadLinesRepository
import com.example.synergym.myorders.ui.HeadLineListner
import com.example.synergym.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HeadLinesViewModel(private val headLinesRepository: HeadLinesRepository) : ViewModel() {
    var headLineListner: HeadLineListner? = null
    lateinit var noteDatabase: NewsDataBase
    val newsData: MutableLiveData<NewsData> by lazy {
        MutableLiveData<NewsData>()
    }

    fun myNewsList(context: Context) {
        noteDatabase =
            Room.databaseBuilder(context, NewsDataBase::class.java, "data_base").build()
        headLineListner?.onStarted()
        Coroutines.main {
            try {
                val newsResponse = headLinesRepository.myNews()
                newsData.value = newsResponse
//                    headLineListner?.onSuccess(newsResponse)
                insertData(newsResponse)
            } catch (e: ApiException) {
                headLineListner?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                getNewsLiveData()
                headLineListner?.onFailure(e.message!!)
            } catch (e: TimeoutException) {
                headLineListner?.onFailure(e.message!!)
            } catch (e: SocketExceptions) {
                headLineListner?.onFailure(e.message!!)
            }
        }
    }

    fun getNewsLiveData() {
        GlobalScope.launch {
            Coroutines.io {
                newsData.postValue(
                    noteDatabase.userDao()!!.fetchAllTasks()
                )
            }
        }
    }

    private fun insertData(newsResponse: NewsData) {
        Coroutines.io {
            noteDatabase!!.userDao()!!.insertTask(newsResponse);
        }
    }

}