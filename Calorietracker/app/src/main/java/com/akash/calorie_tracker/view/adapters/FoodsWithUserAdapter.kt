package com.akash.calorie_tracker.view.adapters


import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akash.calorie_tracker.BASE_URL
import com.akash.calorie_tracker.architecture.manager.SessionManager
import com.akash.calorie_tracker.databinding.FoodWithUserItemBinding
import com.akash.calorie_tracker.domain.models.Food
import com.akash.calorie_tracker.domain.models.FoodWithUserInfo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders


class FoodsWithUserAdapter : PagingDataAdapter<FoodWithUserInfo, FoodsWithUserAdapter.FoodWithUserViewHolder>(
    diffCallback) {


    companion object{
        private val diffCallback: DiffUtil.ItemCallback<FoodWithUserInfo> = object : DiffUtil.ItemCallback<FoodWithUserInfo>() {
            override fun areItemsTheSame(oldItem: FoodWithUserInfo, newItem: FoodWithUserInfo): Boolean {
                return TextUtils.equals(oldItem.id, newItem.id)
            }

            override fun areContentsTheSame(oldItem: FoodWithUserInfo, newItem: FoodWithUserInfo): Boolean {
                return oldItem.name == newItem.name && oldItem.calorie == newItem.calorie && oldItem.date == newItem.date
            }
        } //define AsyncListDiffer
    }





    inner class FoodWithUserViewHolder(val binding: FoodWithUserItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(food: FoodWithUserInfo?){
            food?.let {
                binding.tvName.text = "Name: ${food.name}"
                binding.tvCalorie.text = "Calorie: ${food.calorie}"
                binding.tvDate.text = "Date: ${food.date}"
                binding.tvUsername.text = "CreatedBy: ${food.user?.email}"


                val url = BASE_URL +"images/"+food.image
                val glideUrl = GlideUrl(
                    url,
                    LazyHeaders.Builder()
                        .addHeader("Authorization", "Bearer ${SessionManager.authToken}")
                        .build())

                Glide.with(binding.ivFoodimage)
                    .load(glideUrl)
                    .into(binding.ivFoodimage);
            }
        }


    }






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodWithUserViewHolder {
        val binding = FoodWithUserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FoodWithUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodWithUserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    interface Callback{
        fun onClick(food: FoodWithUserInfo?)
    }



}