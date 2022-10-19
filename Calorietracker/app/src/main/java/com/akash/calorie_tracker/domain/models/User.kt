package com.akash.calorie_tracker.domain.models

import android.util.Log
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class User(
    @Expose
    val id:String,
    val roles:List<String>,
    @Expose
    @SerializedName("email")
    val email:String
){
    val enumRoles:List<Role>

    init {
        enumRoles = extractRoles(roles)
    }

    fun extractRoles(roles:List<String>):List<Role>{
        val rolesEnum = mutableListOf<Role>()

        Log.d("response", "extractRoles: " + roles)
        for(role in roles){
//           val roleTrimmed = role.replace("[","").replace("]","")
            Log.d("response", "extractRoles: " + role)
            when (role.lowercase(Locale.ENGLISH)){
                "role_user"->rolesEnum.add(Role.ROLE_USER)
                "role_admin"->rolesEnum.add(Role.ROLE_ADMIN)
            }
        }
        return rolesEnum
    }

}
