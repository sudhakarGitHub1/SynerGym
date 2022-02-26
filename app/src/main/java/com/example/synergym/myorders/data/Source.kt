package com.example.synergym.myorders.data

import androidx.room.Entity

@Entity
data class Source(
    val id: String,
    val name: String
)