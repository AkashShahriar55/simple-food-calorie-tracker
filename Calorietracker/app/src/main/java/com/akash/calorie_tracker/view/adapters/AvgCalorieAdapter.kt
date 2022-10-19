package com.akash.calorie_tracker.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akash.calorie_tracker.databinding.AvgCalorieItemBinding
import com.akash.calorie_tracker.domain.models.AvgCalorie


class AvgCalorieAdapter : RecyclerView.Adapter<AvgCalorieAdapter.AvgCalorieViewHolder>(){




    private val diffCallback: DiffUtil.ItemCallback<AvgCalorie> = object : DiffUtil.ItemCallback<AvgCalorie>() {
        override fun areItemsTheSame(oldItem: AvgCalorie, newItem: AvgCalorie): Boolean {
            return oldItem.email == newItem.email
        }

        override fun areContentsTheSame(oldItem: AvgCalorie, newItem: AvgCalorie): Boolean {
            return oldItem.email == newItem.email && oldItem.avgCalories == newItem.avgCalories
        }
    } //define AsyncListDiffer

    private val mDiffer = AsyncListDiffer<AvgCalorie>(this, diffCallback)


    inner class AvgCalorieViewHolder(val binding: AvgCalorieItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(food: AvgCalorie){
            binding.tvName.text = food.email
            binding.tvAvgCalorie.text= String.format("%.2f",food.avgCalories) +" cals"
        }


    }

    fun submitList(data: List<AvgCalorie>) {
        mDiffer.submitList(data)
    } //method getItem by position


    fun getItem(position: Int): AvgCalorie {
        return mDiffer.currentList[position]
    } //override the method of Adapter


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvgCalorieViewHolder {
        val binding = AvgCalorieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AvgCalorieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AvgCalorieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
//        return 10
    }


}