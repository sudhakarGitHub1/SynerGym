package com.example.synergym.utils

import android.app.Activity
import android.content.Context

object SessionSave {
    fun saveSession(
        key: String?,
        value: String?,
        context: Context?
    ) {
        if (context != null) {
            val editor =
                context.getSharedPreferences("KEY", Activity.MODE_PRIVATE).edit()
            editor.putString(key, value)
            editor.commit()
        }
    }

    fun getSession(key: String?, context: Context?): String? {
        return if (context != null) {
            val prefs =
                context.getSharedPreferences("KEY", Activity.MODE_PRIVATE)
            prefs.getString(key, "")
        } else {
            ""
        }
    }
}