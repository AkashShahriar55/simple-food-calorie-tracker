package com.akash.calorie_tracker.view.dialog

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.akash.calorie_tracker.databinding.AddFoodDialogBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import java.text.SimpleDateFormat
import java.util.*


class AddFoodBottomSheetDialog() : BottomSheetDialogFragment() {


    var mProfileUri: Uri? = null
    lateinit var binding:AddFoodDialogBinding
    lateinit var datePicker:MaterialDatePicker<Long>

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
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        datePicker.addOnPositiveButtonClickListener { selection ->
            val calendar: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = selection
            val format = SimpleDateFormat("yyyy-MM-dd")
            val formattedDate: String = format.format(calendar.time)
            binding.dateEt.setText(formattedDate)
        }


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
    }

    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop()	    			//Crop image(Optional), Check Customization for more option
            .compress(1024)			//Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }




    fun openDatePicker(){


        datePicker.show(parentFragmentManager,"Date picker")
    }





}