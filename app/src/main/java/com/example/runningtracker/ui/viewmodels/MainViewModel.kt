package com.example.runningtracker.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.runningtracker.repositories.MainRepository


class MainViewModel
@ViewModelInject
constructor(
    val mainRepository: MainRepository
):ViewModel(){

}