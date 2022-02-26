package com.example.synergym.myorders.repository

import com.example.synergym.myorders.data.NewsData
import com.example.synergym.network.Api
import com.example.synergym.network.SafeApiRequest

class HeadLinesRepository(private val api: Api) : SafeApiRequest() {
    suspend fun myNews(): NewsData {
        return apiRequest {
            api.myNewsList()
        }
    }
}