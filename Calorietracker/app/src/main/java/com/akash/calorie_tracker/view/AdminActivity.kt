package com.akash.calorie_tracker.view


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.akash.calorie_tracker.R
import com.akash.calorie_tracker.databinding.ActivityAdminBinding


enum class Navigation{
    FOOD_PAGE,
    REPORT_PAGE,
    NONE
}

class AdminActivity : AppCompatActivity() {

    lateinit var binding:ActivityAdminBinding

    var currentNavigation = Navigation.NONE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goToFoodPage()


//        binding.bottomNavigation.setOnItemReselectedListener {
//            Log.d("nav_check", "onCreate: " + it.title)
//            when(it.itemId){
//                R.id.food_page->{
////                    goToFoodPage()
//                }
//                R.id.report_page->{
////                    goToReportPage()
//                }
//            }
//        }


        binding.bottomNavigation.setOnItemSelectedListener {
            Log.d("nav_check", "onCreate: " + it.title)
            when(it.itemId){
                R.id.food_page->{
                    goToFoodPage()
                }
                R.id.report_page->{
                    goToReportPage()
                }
            }
            return@setOnItemSelectedListener true
        }








    }

    private fun goToReportPage() {
        if(currentNavigation == Navigation.REPORT_PAGE)
            return

        // Create new fragment and transaction
        // Create new fragment and transaction
        val newFragment: Fragment = ReportFragment.newInstance()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
        transaction.replace(R.id.fragment_container, newFragment)

// Commit the transaction

// Commit the transaction
        transaction.commit()

        currentNavigation = Navigation.REPORT_PAGE
    }

    private fun goToFoodPage() {
        if(currentNavigation == Navigation.FOOD_PAGE)
            return

        // Create new fragment and transaction
        // Create new fragment and transaction
        val newFragment: Fragment = FoodListFragment.newInstance()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
        transaction.replace(R.id.fragment_container, newFragment)

// Commit the transaction

// Commit the transaction
        transaction.commit()

        currentNavigation = Navigation.FOOD_PAGE

    }



}