package com.example.runningtracker.repositories

import com.example.runningtracker.db.Run
import com.example.runningtracker.db.RunDAO
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    val runDAO: RunDAO
){
    suspend fun insertRun(run: Run)=runDAO.insertRun(run)

    suspend fun deleteRun(run:Run)=runDAO.deleteRun(run)

    fun getAllRunsSortedByDate()=runDAO.getAllRunsSortedByDate()

    fun getAllRunsSortedByDistance()=runDAO.getAllRunsSortedByDistance()

    fun getAllRunsSortedByTimeInMillis()=runDAO.getAllRunsSortedByTimeInMillis()

    fun getAllRunsSortedByAvgSpeed()=runDAO.getAllRunsSortedByAvgSpeed()

    fun getAllRunsSortedByCaloriesBurned()=runDAO.getAllRunsSortedByCaloriesBurned()

    fun getTotalAvgSpeed()=runDAO.getTotalAvgSpeed()

    fun getTotalCaloriesBurned()=runDAO.getTotalCaloriesBurned()

    fun getTotaltimeInMillis()=runDAO.getAllRunsSortedByTimeInMillis()


}