package com.example.synergym.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.synergym.utils.NoInternetException
import com.example.synergym.utils.SocketExceptions
import com.example.synergym.utils.TimeoutException
import com.example.synergym.utils.isnetworkAvailable
import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketException
import java.net.SocketTimeoutException

class NetworkConnectionIntercepter(
    context: Context
) : Interceptor {
    private val applicationContext = context
    override fun intercept(chain: Interceptor.Chain): Response {
        if (isnetworkAvailable(applicationContext)) {
            try {
                return chain.proceed(chain.request())
            } catch (e: SocketTimeoutException) {
                throw TimeoutException(e.toString())
            } catch (e: SocketException) {
                throw SocketExceptions(e.toString())
            } catch (e: Exception) {
                throw SocketExceptions(e.toString())
            }
        }
        throw NoInternetException("check your internet connection")
    }


}