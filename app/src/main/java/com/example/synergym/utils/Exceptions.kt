package com.example.synergym.utils

import java.io.IOException

class ApiException(message: String) : IOException(message)
class NoInternetException(message: String):IOException(message)
class TimeoutException(message: String):IOException(message)
class SocketExceptions(message: String):IOException(message)