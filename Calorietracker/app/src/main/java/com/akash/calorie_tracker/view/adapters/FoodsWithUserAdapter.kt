package com.akash.calorie_tracker.view.adapters


import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akash.calorie_tracker.databinding.FoodWithUserItemBinding
import com.akash.calorie_tracker.domain.models.Food
import com.akash.calorie_tracker.domain.models.FoodWithUserInfo


class FoodsWithUserAdapter : RecyclerView.Adapter<FoodsWithUserAdapter.FoodWithUserViewHolder>(){




    private val diffCallback: DiffUtil.ItemCallback<FoodWithUserInfo> = object : DiffUtil.ItemCallback<FoodWithUserInfo>() {
        override fun areItemsTheSame(oldItem: FoodWithUserInfo, newItem: FoodWithUserInfo): Boolean {
            return TextUtils.equals(oldItem.id, newItem.id)
        }

        override fun areContentsTheSame(oldItem: FoodWithUserInfo, newItem: FoodWithUserInfo): Boolean {
            return oldItem.name == newItem.name && oldItem.calorie == newItem.calorie && oldItem.date == newItem.date
        }
    } //define AsyncListDiffer

    private val mDiffer = AsyncListDiffer<FoodWithUserInfo>(this, diffCallback)


    inner class FoodWithUserViewHolder(binding: FoodWithUserItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(food: Food){

        }


    }

    fun submitList(data: List<FoodWithUserInfo>) {
        mDiffer.submitList(data)
    } //method getItem by position


    fun getItem(position: Int): FoodWithUserInfo {
        return mDiffer.currentList[position]
    } //override the method of Adapter


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodWithUserViewHolder {
        val binding = FoodWithUserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FoodWithUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodWithUserViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
//        return mDiffer.currentList.size
        return 10
    }


}