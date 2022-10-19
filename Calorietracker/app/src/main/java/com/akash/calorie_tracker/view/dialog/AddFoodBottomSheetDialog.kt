package com.akash.calorie_tracker.view.dialog

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.akash.calorie_tracker.BASE_URL
import com.akash.calorie_tracker.architecture.manager.SessionManager
import com.akash.calorie_tracker.databinding.AddFoodDialogBinding
import com.akash.calorie_tracker.domain.models.FoodCreateRequest
import com.akash.calorie_tracker.domain.models.FoodWithUserInfo
import com.akash.calorie_tracker.domain.models.User
import com.akash.calorie_tracker.helpers.DataFormatHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class AddFoodBottomSheetDialog() : BottomSheetDialogFragment() {


    private var food_id: String? = null
    private var food: FoodWithUserInfo? = null
    private var user: List<User> = arrayListOf()
    private var forAdmin: Boolean = false
    private var dialogCallback: DialogCallback? = null
    var mProfileUri: Uri? = null
    lateinit var binding:AddFoodDialogBinding
    lateinit var datePicker:MaterialDatePicker<Long>
    lateinit var timePicker: MaterialTimePicker
    private var selectedUser:User? = null

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
        binding = AddFoodDialogBinding.inflate(inflater,container,false)
        datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        timePicker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(Calendar.getInstance().get(Calendar.HOUR))
                .setMinute(Calendar.getInstance().get(Calendar.MINUTE))
                .setTitleText("Select time")
                .build()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if(forAdmin) {
            binding.usersSp.visibility = View.VISIBLE
            val some = user.map { user -> user.email }
            Log.d("check", "onViewCreated: " + some)
            selectedUser = user[0]
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line,some)
            binding.usersSp.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        }else{
            binding.usersSp.visibility = View.GONE
        }





        binding.usersSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedUser = user[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedUser = null
            }

        }


        datePicker.addOnPositiveButtonClickListener { selection ->
            val calendar: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = selection
            val format = SimpleDateFormat("yyyy-MM-dd")
            val formattedDate: String = format.format(calendar.time)
            binding.dateEt.setText(formattedDate)
        }


        timePicker.addOnPositiveButtonClickListener {
            val ampm = if(timePicker.hour >=12) "pm" else "am"
            val hour = if(timePicker.hour == 0) 12 else if(timePicker.hour == 12) 12 else timePicker.hour%12
            val minute = timePicker.minute
            val timeString = String.format(Locale.getDefault(), "%02d:%02d $ampm", hour, minute)
            binding.timeEt.setText(timeString)
        }

        val calendar: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val format = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate: String = format.format(calendar.time)
        val timeFormat = SimpleDateFormat("hh:mm aa")
        val formattedtime: String = timeFormat.format(calendar.time)
        binding.dateEt.setText(formattedDate)
        binding.timeEt.setText(formattedtime)

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


        binding.timeEt.setOnClickListener {
            openTimePicker()
        }

        binding.timeEt.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus)
                openTimePicker()
        }



        binding.btnAddFood.setOnClickListener {
            validateData()
        }

        food?.let {
            binding.btnAddFood.visibility = View.GONE
            binding.btnUpdateFood.visibility =View.VISIBLE
            binding.btnDeleteFood.visibility = View.VISIBLE
            binding.usersSp.isEnabled = false
            binding.usersSp.isClickable = false


            binding.usersSp.setSelection(user.indices.find {idx-> user[idx] == food?.user } ?: 0)
            binding.foodNameEt.setText(food?.name)
            binding.calorieEt.setText("${food?.calorie}")
            binding.dateEt.setText(food?.date)
            val _24HourTime = food?.time
            val _24HourSDF = SimpleDateFormat("HH:mm")
            val _12HourSDF = SimpleDateFormat("hh:mm a")
            val _24HourDt = _24HourSDF.parse(_24HourTime)
            val time =  _12HourSDF.format(_24HourDt!!)
            binding.timeEt.setText(time)

            food_id = food?.id
            binding.btnAddImage.text = "Update Image"

            val url = BASE_URL +"images/"+food?.image
            val glideUrl = GlideUrl(
                url,
                LazyHeaders.Builder()
                    .addHeader("Authorization", "Bearer ${SessionManager.authToken}")
                    .build())

            Glide.with(binding.ivFoodImage)
                .load(glideUrl)
                .into(binding.ivFoodImage);


            binding.btnUpdateFood.setOnClickListener {
                validateData()
            }

            binding.btnDeleteFood.setOnClickListener {
                dialogCallback?.onDeleteFood(food)
            }
        }

    }

    private fun openTimePicker() {
        timePicker.show(parentFragmentManager,"Time picker")
    }

    private fun validateData() {
        val name = binding.foodNameEt.text?.toString()
        val calorie = binding.calorieEt.text?.toString()
        val date = binding.dateEt.text?.toString()!!
        val _12HourTime = binding.timeEt.text?.toString()!!
        val _24HourSDF = SimpleDateFormat("HH:mm")
        val _12HourSDF = SimpleDateFormat("hh:mm a")
        val _12HourDt = _12HourSDF.parse(_12HourTime)
        val time =  _24HourSDF.format(_12HourDt!!)+":00"

        Log.d("check", "validateData: " + time)

        if(name.isNullOrEmpty()){
            binding.foodNameTil.error = "Please enter name"
            return
        }


        if(calorie.isNullOrEmpty()){
            binding.calorieTil.error = "Please enter calorie"
            return
        }


        val calorieFloat =  calorie.toFloat()


        var file:File? = null

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


        dialogCallback?.onAddFood(
                FoodCreateRequest(
                    food_id,
                name,
                calorieFloat,
                date,
                    time,
                selectedUser,
                    file
            )
        )


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


        fun newInstance(dialogCallback: DialogCallback?): AddFoodBottomSheetDialog {
            val args = Bundle()

            val fragment = AddFoodBottomSheetDialog()
            fragment.dialogCallback = dialogCallback
            fragment.arguments = args
            return fragment
        }

        fun newInstanceForAdmin(dialogCallback: DialogCallback?,user: List<User>,food:FoodWithUserInfo?): AddFoodBottomSheetDialog {
            val args = Bundle()

            val fragment = AddFoodBottomSheetDialog()
            fragment.forAdmin = true
            fragment.food = food
            fragment.user = user
            fragment.dialogCallback = dialogCallback
            fragment.arguments = args
            return fragment
        }
    }

    fun openDatePicker(){


        datePicker.show(parentFragmentManager,"Date picker")
    }



    interface DialogCallback{
        fun onAddFood(foodCreateRequest: FoodCreateRequest)
        fun onDeleteFood(food: FoodWithUserInfo?)
    }



}