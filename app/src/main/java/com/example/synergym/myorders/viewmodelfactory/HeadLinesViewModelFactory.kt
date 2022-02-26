package com.example.synergym.myorders.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.synergym.myorders.repository.HeadLinesRepository
import com.example.synergym.myorders.viewmodel.HeadLinesViewModel

class HeadLinesViewModelFactory(private val repository: HeadLinesRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HeadLinesViewModel(repository) as T
    }

}