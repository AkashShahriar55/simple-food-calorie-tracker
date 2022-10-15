package com.akash.calorie_tracker.architecture.manager

import android.content.Context
import android.content.SharedPreferences
import com.akash.calorie_tracker.R
import com.akash.calorie_tracker.domain.models.Role
import com.akash.calorie_tracker.domain.models.User
import com.akash.calorie_tracker.helpers.DataFormatHelper

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    private var user:User? = null
    private var authToken :String? = null

    companion object {
        const val USER_TOKEN = "user_token"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(username:String,token: String) {
        val editor = prefs.edit()
        editor.putString(username+":auth", token)
        editor.apply()
    }

    fun saveRoles(username: String,roles:List<String>){
        //Set the values
        //Set the values
        val set: MutableSet<String> = HashSet()
        set.addAll(roles)
        val editor = prefs.edit()
        editor.putStringSet(username+":role", set)
        editor.apply()
    }


    fun getRoles(username: String):List<Role>{
        //Retrieve the values
        //Retrieve the values
        val set = prefs.getStringSet(username+":role", null)
        set?.let {
            return DataFormatHelper.extractRoles(it.toList());
        }
        return arrayListOf()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(username: String): String? {
        return prefs.getString(username+":auth", null)
    }

    fun getAuthToken(): String? {
        return authToken
    }

    fun setUser(user: User,authToken:String) {
        this.authToken = authToken
        this.user = user
        saveAuthToken(user.email,authToken)
        saveRoles(user.email,user.roles)
    }

    fun login(email: String): Boolean {
        this.authToken  =fetchAuthToken(email)

        return authToken != null

    }


}