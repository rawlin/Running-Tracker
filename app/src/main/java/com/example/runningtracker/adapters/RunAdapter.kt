package com.example.runningtracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.runningtracker.R
import com.example.runningtracker.databinding.ItemRunBinding
import com.example.runningtracker.db.Run
import com.other.TrackingUtility
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    inner class  RunViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val binding= ItemRunBinding.bind(itemView)

    }

    val diffCallback=object : DiffUtil.ItemCallback<Run>(){
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return  oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode()==newItem.hashCode()
        }
    }

    val differ=AsyncListDiffer(this,diffCallback)

    fun submitList(list:List<Run>)=differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_run,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run =differ.currentList[position]

        with(holder){
            Glide.with(this.itemView).load(run.img).into(binding.ivRunImage)

            val calendar=Calendar.getInstance().apply {
                timeInMillis=run.timestamp
            }
            val dateFormat=SimpleDateFormat("dd.MM.yy",Locale.getDefault())
            binding.tvDate.text=dateFormat.format(calendar.time)

            val avgSpeed="${run.avgSpeedInKMH}km/h"
            binding.tvAvgSpeed.text=avgSpeed

            binding.tvTime.text=TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

            val caloriesBurned="${run.caloriesBurned}kcal"
            binding.tvCalories.text=caloriesBurned

        }

    }

    override fun getItemCount(): Int =differ.currentList.size
}