package com.akash.calorie_tracker.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akash.calorie_tracker.databinding.ActivityUserBinding
import com.akash.calorie_tracker.view.adapters.FoodsAdapter
import com.akash.calorie_tracker.view.dialog.AddFoodBottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialog


class UserActivity : AppCompatActivity() {


    lateinit var binding: ActivityUserBinding
    var foodsAdapter = FoodsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.floatingActionButton.setOnClickListener {
            showAddFoodDialog()
        }


        setupRecyclerView()


    }

    private fun setupRecyclerView() {
        binding.rvFoodItem.layoutManager = LinearLayoutManager(this)
        binding.rvFoodItem.adapter = foodsAdapter

    }

    private fun showAddFoodDialog() {
       val addFoodDialog = AddFoodBottomSheetDialog()
        addFoodDialog.show(supportFragmentManager,"Add Food")
    }
}