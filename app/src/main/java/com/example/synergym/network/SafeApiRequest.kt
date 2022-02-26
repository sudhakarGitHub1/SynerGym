package com.example.synergym.network

import com.example.synergym.utils.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val reponse = call.invoke()
        if (reponse.isSuccessful) {
            return reponse.body()!!
        } else {
            val error = reponse.errorBody()?.toString()
            val message: StringBuffer? = null
            error?.let {
                try {
                    message!!.append(JSONObject(it).getString("message"))
                } catch (e: JSONException) {
                    throw ApiException(message.toString())

                }
                message!!.append("/n")
            }
            message!!.append("Error code ${reponse.code()}")
            throw ApiException(message.toString())
        }
    }

}