package com.akash.calorie_tracker.view.adapters


import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akash.calorie_tracker.BASE_URL
import com.akash.calorie_tracker.architecture.manager.SessionManager
import com.akash.calorie_tracker.databinding.FoodItemBinding
import com.akash.calorie_tracker.domain.models.Food
import com.akash.calorie_tracker.domain.models.User
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders


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


    inner class FoodViewHolder(val binding: FoodItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(food: Food){

            binding.tvName.text = "Name: ${food.name}"
            binding.tvCalorie.text = "Calorie: ${food.calorie}"
            binding.tvDate.text = "Date: ${food.date}"

            val url = BASE_URL+"images/"+food.image
            val glideUrl = GlideUrl(
                url,
                LazyHeaders.Builder()
                    .addHeader("Authorization", "Bearer ${SessionManager.authToken}")
                    .build())

            Glide.with(binding.ivFood)
                .load(glideUrl)
                .into(binding.ivFood);
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
        holder.bind(mDiffer.currentList[position])
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
//        return 10
    }

    fun addFood(food: Food) {
        val currentList = ArrayList(mDiffer.currentList)
        currentList.add(food)
        submitList(currentList)

    }


}