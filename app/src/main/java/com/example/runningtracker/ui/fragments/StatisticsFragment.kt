package com.example.runningtracker.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.runningtracker.R
import com.example.runningtracker.databinding.FragmentStatisticsBinding
import com.example.runningtracker.other.CustomMarkerView
import com.example.runningtracker.ui.viewmodels.StatisticsViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.example.runningtracker.other.TrackingUtility
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
        setupBarChart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    private fun setupBarChart(){
        binding.barChart.xAxis.apply {
            position=XAxis.XAxisPosition.BOTTOM
            setDrawLabels(false)
            axisLineColor=Color.WHITE
            textColor=Color.WHITE
        }
        binding.barChart.axisLeft.apply {
            axisLineColor=Color.WHITE
            textColor=Color.WHITE
        }
        binding.barChart.axisRight.apply {
            axisLineColor=Color.WHITE
            textColor=Color.WHITE
        }
        binding.barChart.apply {
            description.text="Avg Speed Vs Time"
            legend.isEnabled=false
        }
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

        viewModel.runsSortedByDate.observe(viewLifecycleOwner,Observer{
            it?.let{
                val allAvgSpeeds=it.indices.map { i-> BarEntry(i.toFloat(),it[i].avgSpeedInKMH) }
                val barDataSet=BarDataSet(allAvgSpeeds,"Avg Speed Vs Time").apply {
                    valueTextColor=Color.WHITE
                    color=ContextCompat.getColor(requireContext(),R.color.colorAccent)
                }
                binding.barChart.data= BarData(barDataSet)
                binding.barChart.marker=CustomMarkerView(it.reversed(),requireContext(),R.layout.marker_view)
                binding.barChart.invalidate()
            }
        })
    }
}