package com.akash.calorie_tracker.helpers

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.util.Log
import com.akash.calorie_tracker.domain.models.Role
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.math.roundToInt

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

    fun imageToString(imageUri:Uri?,contentResolver: ContentResolver?): String {
        if(imageUri == null)
            return  " "

        if(contentResolver == null)
            return " "
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        val byteArrayOutputStream = ByteArrayOutputStream()
        val aspectRatio: Float = bitmap.width.toFloat() / bitmap.height.toFloat()
        val width = 1080
        val height = (width / aspectRatio).roundToInt()
        var bitm = Bitmap.createScaledBitmap(bitmap, width, height, false)
        bitm.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        val imgBytes = byteArrayOutputStream.toByteArray()
        return encodeToString(imgBytes, DEFAULT)
    }
}