package com.example.synergym.myorders.ui

import com.example.synergym.myorders.data.NewsData

interface HeadLineListner  {
    fun onSuccess(loginResponse: NewsData)
    fun onFailure(message: String)
    fun onStarted()
    fun onCLick(id:Int)
}