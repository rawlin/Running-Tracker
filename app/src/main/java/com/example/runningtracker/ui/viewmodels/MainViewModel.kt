package com.example.runningtracker.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runningtracker.db.Run
import com.example.runningtracker.repositories.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel
@ViewModelInject
constructor(
    val mainRepository: MainRepository
):ViewModel(){

    val runsSortedByDate=mainRepository.getAllRunsSortedByDate()

    fun insertRun(run: Run)=viewModelScope.launch(Dispatchers.IO) {
        mainRepository.insertRun(run)
    }
}