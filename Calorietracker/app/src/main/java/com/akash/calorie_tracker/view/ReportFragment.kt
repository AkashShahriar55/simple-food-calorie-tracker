package com.akash.calorie_tracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.akash.calorie_tracker.R
import com.akash.calorie_tracker.databinding.FragmentFoodListPageBinding
import com.akash.calorie_tracker.databinding.FragmentReportBinding
import com.akash.calorie_tracker.domain.models.AvgCalories
import com.akash.calorie_tracker.view.adapters.AvgCalorieAdapter
import com.akash.calorie_tracker.view.adapters.FoodsWithUserAdapter

class ReportFragment : Fragment() {

    var avgCalorieAdapter = AvgCalorieAdapter()
    lateinit var  binding: FragmentReportBinding
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
        binding = FragmentReportBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvAvgCalorie.layoutManager = LinearLayoutManager(context)
        binding.rvAvgCalorie.adapter = avgCalorieAdapter

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ReportFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}