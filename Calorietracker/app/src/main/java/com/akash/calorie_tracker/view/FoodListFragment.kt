package com.akash.calorie_tracker.view

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.akash.calorie_tracker.R
import com.akash.calorie_tracker.architecture.viewmodels.AdminViewModel
import com.akash.calorie_tracker.databinding.ActivityAdminBinding
import com.akash.calorie_tracker.databinding.FragmentFoodListPageBinding
import com.akash.calorie_tracker.domain.models.FoodCreateRequest
import com.akash.calorie_tracker.view.adapters.FoodsAdapter
import com.akash.calorie_tracker.view.adapters.FoodsWithUserAdapter
import com.akash.calorie_tracker.view.dialog.AddFoodBottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodListFragment : Fragment() {

    var foodsWithUserAdapter = FoodsWithUserAdapter()
    lateinit var  binding: FragmentFoodListPageBinding
    var progressDialog: ProgressDialog? = null

    val adminViewModel:AdminViewModel by activityViewModels()
    var dialogCallback:AddFoodBottomSheetDialog.DialogCallback? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFoodListPageBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        binding.floatingActionButton.setOnClickListener {
            showAddFoodDialog()
        }

        dialogCallback =  object : AddFoodBottomSheetDialog.DialogCallback{
            override fun onAddFood(foodCreateRequest: FoodCreateRequest) {
                Log.d("add_food", "onAddFood: ")
                progressDialog?.show()
                adminViewModel.createFood(foodCreateRequest).observe(viewLifecycleOwner){
                    Log.d("test", "onAddFood: " + it)
                    progressDialog?.dismiss()
                    it?.let {
                        foodsWithUserAdapter.refresh()
                        addFoodDialog?.dismiss()

                    }
                }
            }

        }

        Log.d("check", "onViewCreated: ")
        // Collect from the PagingData Flow in the ViewModel, and submit it to the
        // PagingDataAdapter.
        lifecycleScope.launch {
            adminViewModel.foodWithUserItems.collectLatest {
                Log.d("paging_test", "onCreate: " + it)
                foodsWithUserAdapter.submitData(it)
            }
        }


        // Use the CombinedLoadStates provided by the loadStateFlow on the ArticleAdapter to
        // show progress bars when more data is being fetched
        lifecycleScope.launch {
            foodsWithUserAdapter.loadStateFlow.collect {
                binding.prependProgress.isVisible = it.source.prepend is LoadState.Loading
                binding.appendProgress.isVisible = it.source.append is LoadState.Loading
            }
        }
    }

    var addFoodDialog:AddFoodBottomSheetDialog? = null
    private fun setupRecyclerView() {
        binding.rvFoodItem.layoutManager = LinearLayoutManager(context)
        binding.rvFoodItem.adapter = foodsWithUserAdapter

    }

    private fun showAddFoodDialog() {
        addFoodDialog = AddFoodBottomSheetDialog.newInstance(dialogCallback)
        addFoodDialog?.show(childFragmentManager,"Add Food")
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            FoodListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}