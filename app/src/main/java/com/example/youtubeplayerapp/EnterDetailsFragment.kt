package com.example.youtubeplayerapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.youtubeplayerapp.databinding.FragmentEnterDetailsBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class EnterDetailsFragment : Fragment() {

    class Run {
        companion object {
            fun after(delay: Long, process: () -> Unit) {
                Handler().postDelayed({
                    process()
                }, delay)
            }
        }
    }

    private lateinit var binding: FragmentEnterDetailsBinding
    private val viewModel: myViewModel by activityViewModels()
    lateinit var currentPhotoPath: String
    lateinit var photoFile: File
    lateinit var uri: Uri
    //var dt:Boolean=false


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
        binding = FragmentEnterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.capture.setOnClickListener {
            openActivityForResult()
            viewModel.imageClicked(true)

            //binding.img.setImageURI(viewModel.getUri())
        }
        binding.next.setOnClickListener {

            if (binding.name.text.toString() != "" && binding.email.text.toString() != "" && viewModel.checkimgClicked() == true &&emailValidator(binding.email)==true) {
                viewModel.saveName(binding.name.text.toString())
                viewModel.saveEmail(binding.email.text.toString())
                requireView().findNavController()
                    .navigate(R.id.action_enterDetailsFragment_to_formFragment)
            }
            else if (emailValidator(binding.email)==false)
                Toast.makeText(requireContext(), "Invalid Email/Enter Email", Toast.LENGTH_SHORT).show()
            else {
                Toast.makeText(requireActivity(), "Please enter all details/Make sure Image is captured", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        if(viewModel.checkimgClicked())
            binding.img.setImageURI(viewModel.getUri())

    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                binding.img.setImageURI(viewModel.getUri())
            }
        }

    private fun openActivityForResult() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1)
        intent.putExtra("com.google.assistant.extra.USE_FRONT_CAMERA", true)
        intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true)
        intent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1)
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1)
        intent.putExtra("camerafacing", "front")
        intent.putExtra("previous_mode", "front")

        photoFile = createImageFile()
//        uri=FileProvider.getUriForFile(
//            requireContext(),
//            "com.example.retrofittest.fileprovider",
//            photoFile
//        )
        viewModel.setUri(
            FileProvider.getUriForFile(
                requireContext(),
                "com.example.retrofittest.fileprovider",
                photoFile
            )
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, viewModel.getUri())
        resultLauncher.launch(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            try {
                if (data != null) {
                    uri = data.data!!
                    viewModel.setUri(uri)
                    //dt=true
                    //homeScreenBinding.myImage.setImageURI(uri)
                    //binding.img.setImageURI(viewModel.getUri())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

//    fun isEmailValid(email: String): Boolean {
//        var isValid = false
//        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
//        val inputStr: CharSequence = email
//        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
//        val matcher = pattern.matcher(inputStr)
//        if (matcher.matches()) {
//            isValid = true
//        }
//        return isValid
//    }

    override fun onResume() {
        super.onResume()
        this.requireView().isFocusableInTouchMode = true
        this.requireView().requestFocus()
        this.requireView().setOnKeyListener { _, keyCode, _ ->
            keyCode == KeyEvent.KEYCODE_BACK
        }
    }

    fun emailValidator(etMail: EditText):Boolean {


        val emailToText = etMail.text.toString()


        return !emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()
    }

}