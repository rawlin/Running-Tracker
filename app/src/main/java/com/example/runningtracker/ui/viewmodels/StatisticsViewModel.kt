package com.example.runningtracker.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.runningtracker.repositories.MainRepository


class StatisticsViewModel
@ViewModelInject
constructor(
    val mainRepository: MainRepository
):ViewModel(){

}