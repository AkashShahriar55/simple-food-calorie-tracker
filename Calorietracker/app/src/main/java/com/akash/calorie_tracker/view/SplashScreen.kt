package com.akash.calorie_tracker.view

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer

import com.akash.calorie_tracker.architecture.manager.SessionManager
import com.akash.calorie_tracker.architecture.viewmodels.LoginViewModel
import com.akash.calorie_tracker.databinding.ActivitySplashScreenBinding
import com.akash.calorie_tracker.domain.models.LoginRequest
import com.akash.calorie_tracker.domain.models.Role
import com.akash.calorie_tracker.domain.models.Status
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import javax.inject.Inject

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */


@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val loginViewModel:LoginViewModel by viewModels()

    @Inject lateinit var sessionManager:SessionManager


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val username = "akashshahriar.admin@gmail.com";
        val password = "akash"


        val alreadyLoggedIn = loginViewModel.login(LoginRequest(username,password))

        if(alreadyLoggedIn)
            onLoggedInSuccessFull(
                loginViewModel.getRoles(username)
            )

        loginViewModel.loginResponse.observe(this, Observer {
            Log.d("response", "onCreate: " + it)
            it?.let {
                if(it.status == Status.Login_successfull){
                    onLoggedInSuccessFull(it.data?.roles!!)
                }else{
                    onLoggedInFailed()
                }

            }
        })

        edgeToEdge {
            Edge.All
        }
    }

    private fun onLoggedInFailed() {
        showLoginFailedAlert()
    }

    private fun onLoggedInSuccessFull(roles: List<Role>) {

        if(roles.contains(Role.ROLE_ADMIN)){
            goToAdminActivity()
        }else if(roles.contains(Role.ROLE_USER)){
            goToUserActivity()
        }
        else{
            goToUserActivity()
        }

    }

    private fun showLoginFailedAlert() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Login Failed")
            .setMessage("Email or password is wrong . App will exit now.")
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which -> finish()  })
            .setCancelable(false)
            .show()
    }

    private fun goToUserActivity() {
        val intent = Intent(this,UserActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToAdminActivity() {
        val intent = Intent(this,AdminActivity::class.java)
        startActivity(intent)
        finish()
    }


}