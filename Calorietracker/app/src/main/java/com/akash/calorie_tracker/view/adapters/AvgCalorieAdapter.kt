package com.akash.calorie_tracker.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akash.calorie_tracker.databinding.AvgCalorieItemBinding
import com.akash.calorie_tracker.domain.models.AvgCalories
import com.akash.calorie_tracker.domain.models.Food


class AvgCalorieAdapter : RecyclerView.Adapter<AvgCalorieAdapter.AvgCalorieViewHolder>(){




    private val diffCallback: DiffUtil.ItemCallback<AvgCalories> = object : DiffUtil.ItemCallback<AvgCalories>() {
        override fun areItemsTheSame(oldItem: AvgCalories, newItem: AvgCalories): Boolean {
            return oldItem.user == newItem.user
        }

        override fun areContentsTheSame(oldItem: AvgCalories, newItem: AvgCalories): Boolean {
            return oldItem.user == newItem.user && oldItem.avgCalorie == newItem.avgCalorie
        }
    } //define AsyncListDiffer

    private val mDiffer = AsyncListDiffer<AvgCalories>(this, diffCallback)


    inner class AvgCalorieViewHolder(binding: AvgCalorieItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(food: Food){

        }


    }

    fun submitList(data: List<AvgCalories>) {
        mDiffer.submitList(data)
    } //method getItem by position


    fun getItem(position: Int): AvgCalories {
        return mDiffer.currentList[position]
    } //override the method of Adapter


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvgCalorieViewHolder {
        val binding = AvgCalorieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AvgCalorieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AvgCalorieViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
//        return mDiffer.currentList.size
        return 10
    }


}