package com.akash.calorie_tracker.helpers

import android.util.Log
import com.akash.calorie_tracker.domain.models.Role
import java.util.*

object DataFormatHelper {

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