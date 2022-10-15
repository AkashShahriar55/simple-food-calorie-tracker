package com.akash.calorie_tracker.view.adapters


import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akash.calorie_tracker.databinding.FoodItemBinding
import com.akash.calorie_tracker.domain.models.Food
import com.akash.calorie_tracker.domain.models.User


class FoodsAdapter : RecyclerView.Adapter<FoodsAdapter.FoodViewHolder>(){




    private val diffCallback: DiffUtil.ItemCallback<Food> = object : DiffUtil.ItemCallback<Food>() {
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return TextUtils.equals(oldItem.id, newItem.id)
        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.name == newItem.name && oldItem.calorie == newItem.calorie && oldItem.date == newItem.date
        }
    } //define AsyncListDiffer

    private val mDiffer = AsyncListDiffer<Food>(this, diffCallback)


    inner class FoodViewHolder(binding: FoodItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(food: Food){

        }


    }

    fun submitList(data: List<Food>) {
        mDiffer.submitList(data)
    } //method getItem by position


    fun getItem(position: Int): Food {
        return mDiffer.currentList[position]
    } //override the method of Adapter


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = FoodItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
//        return mDiffer.currentList.size
        return 10
    }


}