package com.akash.calorie_tracker.view

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Pair
import androidx.recyclerview.widget.LinearLayoutManager
import com.akash.calorie_tracker.R
import com.akash.calorie_tracker.architecture.viewmodels.UserViewModel
import com.akash.calorie_tracker.databinding.ActivityUserBinding
import com.akash.calorie_tracker.domain.models.FoodCreateRequest
import com.akash.calorie_tracker.domain.models.FoodWithUserInfo
import com.akash.calorie_tracker.domain.models.Status
import com.akash.calorie_tracker.view.adapters.FoodsAdapter
import com.akash.calorie_tracker.view.dialog.AddFoodBottomSheetDialog
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class UserActivity : AppCompatActivity() {


    lateinit var binding: ActivityUserBinding
    var foodsAdapter = FoodsAdapter()
    var addFoodDialog:AddFoodBottomSheetDialog? = null
    val today: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

    val filterFromDay:Calendar =  Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    val filterToDay =  Calendar.getInstance(TimeZone.getTimeZone("UTC"))

    val format = SimpleDateFormat("yyyy-MM-dd")

    var dialogCallback:AddFoodBottomSheetDialog.DialogCallback? = null


    var isFilterOn = false

    var isLoading = true

    var progressDialog:ProgressDialog? = null

     val clientViewModel:UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.floatingActionButton.setOnClickListener {
            showAddFoodDialog()
        }




        progressDialog =  ProgressDialog(this)
        progressDialog?.isIndeterminate = true;
        progressDialog?.setMessage("Loading...")


        setupRecyclerView()

        dialogCallback =  object : AddFoodBottomSheetDialog.DialogCallback{
            override fun onAddFood(foodCreateRequest: FoodCreateRequest) {
                Log.d("add_food", "onAddFood: ")
                progressDialog?.show()
                clientViewModel.createFood(foodCreateRequest).observe(this@UserActivity){
                    Log.d("test", "onAddFood: " + it)
                    progressDialog?.dismiss()
                    it?.let {
                        if(isFilterOn){
                            val fromDate: String = format.format(filterFromDay.time)
                            val toDate = format.format(filterToDay.time)
                            filterData(fromDate,toDate)
                        }else{
                            fetchTheDay()
                        }
                        addFoodDialog?.dismiss()

                    }
                }
            }

            override fun onDeleteFood(food: FoodWithUserInfo?) {

            }

        }


        fetchTheDay()
        
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.previous-> goToPreviousDate()
                R.id.next->goToNextDate()
                R.id.filter->openFilerPicker()
                R.id.logout->logout()
            }
            return@setOnMenuItemClickListener true
        }



        clientViewModel.getFoodDataLiveData().observe(this) { foods ->
            foodsAdapter.submitList(foods)
        }

        clientViewModel.dailyCalorieLimitLiveData().observe(this){
            binding.progressDailyLimit.max = it.calorieLimit.toInt()
            binding.progressDailyLimit.progress = it.consumedCalorie.toInt()

            binding.tvLimits.text = "${it.consumedCalorie.toInt()}/${it.calorieLimit.toInt()}"
        }


    }

    private fun logout() {
        clientViewModel.logout()
        finish()
    }


    private fun openFilerPicker() {
        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .setSelection(
                    Pair(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
                )
                .build()

        dateRangePicker.show(supportFragmentManager,"Date Range Picker")
        dateRangePicker.addOnPositiveButtonClickListener {

            filterFromDay.timeInMillis = it.first
            val fromDate: String = format.format(filterFromDay.time)
            filterToDay.timeInMillis = it.second
            val toDate = format.format(filterToDay.time)

            filterData(fromDate,toDate)
        }
    }

    private fun filterData(fromDate: String, toDate: String) {
        binding.loadingProgress.visibility = View.VISIBLE
        binding.toolbar.title = fromDate
        binding.toolbar.subtitle = "to $toDate"
        binding.dailyLimit.visibility = View.GONE
        isFilterOn = true
        binding.toolbar.menu.getItem(0).isVisible = false
        binding.toolbar.menu.getItem(1).isVisible = false
        clientViewModel.fetchFoodData(fromDate,toDate).observe(this){
            if(it.status == Status.SUCCESSFUL)
                binding.loadingProgress.visibility = View.GONE
            else
                showFailedAlertDialog(it.title,it.message)
        }

    }


    private fun fetchTheDay(){
        binding.loadingProgress.visibility = View.VISIBLE
        val formattedDate: String = format.format(today.time)

        binding.toolbar.title = formattedDate

        clientViewModel.fetchFoodData(formattedDate,formattedDate).observe(this){
            if(it.status == Status.SUCCESSFUL)
                binding.loadingProgress.visibility = View.GONE
            else
                showFailedAlertDialog(it.title,it.message)
        }
    }

    private fun goToNextDate() {
        today.add(Calendar.DAY_OF_YEAR, 1)
        fetchTheDay()
    }

    private fun goToPreviousDate() {
        today.add(Calendar.DAY_OF_YEAR, -1)
        fetchTheDay()
    }

    private fun showFailedAlertDialog(title: String, message: String) {
        binding.loadingProgress.visibility = View.GONE
        progressDialog?.dismiss()
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
            .setCancelable(false)
            .show()
    }

    private fun setupRecyclerView() {
        binding.rvFoodItem.layoutManager = LinearLayoutManager(this)
        binding.rvFoodItem.adapter = foodsAdapter

    }

    override fun onBackPressed() {
        if(isFilterOn){
            resetFilter()
            return
        }
        super.onBackPressed()
    }

    private fun resetFilter() {
        isFilterOn = false
        binding.dailyLimit.visibility = View.VISIBLE
        binding.toolbar.menu.getItem(0).isVisible = true
        binding.toolbar.menu.getItem(1).isVisible = true
        binding.toolbar.subtitle = null
        fetchTheDay()
    }

    private fun showAddFoodDialog() {
        addFoodDialog = AddFoodBottomSheetDialog.newInstance(dialogCallback)
        addFoodDialog?.show(supportFragmentManager,"Add Food")
    }
}