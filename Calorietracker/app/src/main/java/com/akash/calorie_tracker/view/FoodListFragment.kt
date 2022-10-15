package com.akash.calorie_tracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.akash.calorie_tracker.R
import com.akash.calorie_tracker.databinding.ActivityAdminBinding
import com.akash.calorie_tracker.databinding.FragmentFoodListPageBinding
import com.akash.calorie_tracker.view.adapters.FoodsAdapter
import com.akash.calorie_tracker.view.adapters.FoodsWithUserAdapter

class FoodListFragment : Fragment() {

    var foodsWithUserAdapter = FoodsWithUserAdapter()
    lateinit var  binding: FragmentFoodListPageBinding
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
    }


    private fun setupRecyclerView() {
        binding.rvFoodItem.layoutManager = LinearLayoutManager(context)
        binding.rvFoodItem.adapter = foodsWithUserAdapter

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