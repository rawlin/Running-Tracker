package com.example.runningtracker.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.runningtracker.R
import com.example.runningtracker.databinding.FragmentSettingsBinding
import com.example.runningtracker.databinding.FragmentStatisticsBinding
import com.example.runningtracker.ui.viewmodels.MainViewModel
import com.example.runningtracker.ui.viewmodels.StatisticsViewModel
import com.other.TrackingUtility
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.round

@AndroidEntryPoint
class StatisticsFragment:Fragment(R.layout.fragment_statistics) {

    private var _binding: FragmentStatisticsBinding?=null
    private val binding get() = _binding!!
    private val viewModel: StatisticsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentStatisticsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    private fun subscribeToObservers(){
        viewModel.totalTimeRun.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalTimeRun= TrackingUtility.getFormattedStopWatchTime(it)
                binding.tvTotalTime.text=totalTimeRun
            }
        })

        viewModel.totalDistance.observe(viewLifecycleOwner, Observer {
            it?.let{
                val km=it/1000f
                val totalDistance=round(km*10f)/10f
                val totalDistanceString="${totalDistance}km"
                binding.tvTotalDistance.text=totalDistanceString
            }
        })

        viewModel.totalAvgSpeed.observe(viewLifecycleOwner, Observer {
            it?.let {
                val avgSpeed= round(it*10f)/10f
                val avgSpeedString="${avgSpeed}km/h"
                binding.tvAverageSpeed.text=avgSpeedString
            }
        })

        viewModel.totalCaloriesBurned.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalCalories="${it}kcal"
                binding.tvTotalCalories.text=totalCalories
            }
        })
    }
}