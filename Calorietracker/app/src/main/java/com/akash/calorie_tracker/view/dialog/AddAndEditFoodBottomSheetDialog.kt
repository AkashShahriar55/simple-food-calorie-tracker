package com.akash.calorie_tracker.view.dialog

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.akash.calorie_tracker.BASE_URL
import com.akash.calorie_tracker.architecture.manager.SessionManager
import com.akash.calorie_tracker.databinding.AddAndEditFoodDialogBinding
import com.akash.calorie_tracker.databinding.AddFoodDialogBinding
import com.akash.calorie_tracker.domain.models.Food
import com.akash.calorie_tracker.domain.models.FoodCreateRequest
import com.akash.calorie_tracker.domain.models.FoodWithUserInfo
import com.akash.calorie_tracker.helpers.DataFormatHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class AddAndEditFoodBottomSheetDialog() : BottomSheetDialogFragment() {


    private var dialogCallback: EditDialogCallback? = null
    var mProfileUri: Uri? = null
    lateinit var binding:AddAndEditFoodDialogBinding
    lateinit var datePicker:MaterialDatePicker<Long>

    lateinit var food:FoodWithUserInfo

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

                mProfileUri = fileUri


                binding.ivFoodImage.setImageURI(mProfileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddAndEditFoodDialogBinding.inflate(inflater,container,false)
        datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.dateEt.setText(food.date)
        binding.foodNameEt.setText(food.name)
        binding.calorieEt.setText(food.name)

        val url = BASE_URL +"images/"+food.image
        val glideUrl = GlideUrl(
            url,
            LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer ${SessionManager.authToken}")
                .build())

        Glide.with(binding.ivFoodImage)
            .load(glideUrl)
            .into(binding.ivFoodImage);

        datePicker.addOnPositiveButtonClickListener { selection ->
            val calendar: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = selection
            val format = SimpleDateFormat("yyyy-MM-dd")
            val formattedDate: String = format.format(calendar.time)
            binding.dateEt.setText(formattedDate)
        }

        val calendar: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val format = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate: String = format.format(calendar.time)
        binding.dateEt.setText(formattedDate)

        binding.btnAddImage.setOnClickListener {
            openImagePicker()
        }

        binding.dateEt.inputType = InputType.TYPE_NULL
        binding.dateEt.setOnKeyListener(null)
        binding.dateEt.setOnClickListener {
            openDatePicker()
        }
        binding.dateEt.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus)
                openDatePicker()
        }



        binding.btnAddFood.setOnClickListener {
            validateData()
        }

    }

    private fun validateData() {
        val name = binding.foodNameEt.text?.toString()
        val calorie = binding.calorieEt.text?.toString()
        val date = binding.dateEt.text?.toString()!!


        if(name.isNullOrEmpty()){
            binding.foodNameTil.error = "Please enter name"
            return
        }


        if(calorie.isNullOrEmpty()){
            binding.calorieTil.error = "Please enter calorie"
            return
        }


        val calorieFloat =  calorie.toFloat()


        var file: File? = null

        mProfileUri?.let { uri->
            context?.let { context->
                context.contentResolver?.let { contentResolver ->
                    val path = DataFormatHelper.getPath(uri,contentResolver)
                    Log.d("image_check", "validateData: " + path)
                    file = File(uri.path)
                }
            }
        }


        Log.d("image_check", "validateData: $file")





    }

    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop()	    			//Crop image(Optional), Check Customization for more option
            .compress(100)			//Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }




    companion object{
        fun newInstance(dialogCallback: EditDialogCallback?,food: FoodWithUserInfo): AddAndEditFoodBottomSheetDialog {
            val args = Bundle()

            val fragment = AddAndEditFoodBottomSheetDialog()
            fragment.food = food
            fragment.dialogCallback = dialogCallback
            fragment.arguments = args
            return fragment
        }
    }

    fun openDatePicker(){


        datePicker.show(parentFragmentManager,"Date picker")
    }



    interface EditDialogCallback{
        fun onEditFood(foodCreateRequest: FoodCreateRequest)
    }





}