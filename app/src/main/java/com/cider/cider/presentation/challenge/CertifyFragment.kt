package com.cider.cider.presentation.challenge

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeCertifyBinding
import com.cider.cider.domain.model.ImageCardModel
import com.cider.cider.domain.type.challenge.ImageType
import com.cider.cider.utils.binding.BindingFragment
import java.util.logging.Logger

class CertifyFragment: BindingFragment<FragmentChallengeCertifyBinding>(R.layout.fragment_challenge_certify) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButton()
        setCamera()
    }

    private fun setCamera() {
        binding.btnImage.setOnClickListener {
            requestPermission()
        }
    }



    private fun setButton() {
        binding.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            openCamera()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openCamera()
            } else {

            }
        }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.extras
                Log.d("TEST Camera","$selectedImageUri")

                binding.ivImage.setImageBitmap(selectedImageUri?.get("data") as Bitmap)
                // Call a function to set the image from the URI to the ImageView
                //setImageFromUri(selectedImageUri)
            }
        }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }

    private fun setImageFromUri(imageUri: Uri?) {
        try {
            if (imageUri != null) {
                Log.d("TEST image", "add Image success $imageUri")

                Glide.with(this)
                    .load(imageUri)
                    .into(binding.ivImage)
            } else {
                Log.d("TEST image", "add Image fail")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}