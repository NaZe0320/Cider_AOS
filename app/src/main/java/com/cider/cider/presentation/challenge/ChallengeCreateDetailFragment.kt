package com.cider.cider.presentation.challenge

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeCreateDetailBinding
import com.cider.cider.domain.model.ImageCardModel
import com.cider.cider.domain.type.challenge.ImageType
import com.cider.cider.presentation.adapter.ImageListAdapter
import com.cider.cider.presentation.dialog.CapacityBottomSheetDialog
import com.cider.cider.presentation.viewmodel.ChallengeCreateViewModel
import com.cider.cider.utils.ItemSpacingDecoration
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChallengeCreateDetailFragment: BindingFragment<FragmentChallengeCreateDetailBinding>(R.layout.fragment_challenge_create_detail) {

    private val viewModel: ChallengeCreateViewModel by activityViewModels()

    private lateinit var imageType: ImageType

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.challenge = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setButton()
        setEditText()
        setRecyclerView()
    }

    private fun setEditText() {
        binding.etChallengeTitle.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.tvChallengeTitleAlert.visibility =
                    if ((viewModel.challengeTitle.value?.length ?: 0) < 5) View.VISIBLE else View.INVISIBLE
            }
        }

        binding.etChallengeIntroduction.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.tvChallengeIntroductionAlert.visibility =
                    if ((viewModel.challengeIntroduction.value?.length ?: 0) < 30) View.VISIBLE else View.INVISIBLE
            }
        }

        binding.etChallengeAuthentication.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.tvChallengeAuthentication.visibility =
                    if ((viewModel.challengeAuthentication.value?.length ?: 0) < 5) View.VISIBLE else View.INVISIBLE
            }
        }

        binding.etChallengeAuthentication.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.etChallengeAuthentication.clearFocus()
                hideKeyBoard()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun setButton() {
        binding.btnChallengeCreate.setOnClickListener {
            findNavController().navigate(
                R.id.action_challengeCreateDetailFragment_to_challengeCreateCheckFragment
            )
        }

        binding.btnChallengeCapacityPeople.setOnClickListener {
            hideKeyBoard()
            showCapacityBottomSheetDialog()
        }

        binding.btnChallengeRecruitmentPeriodDown.setOnClickListener {
            hideKeyBoard()
            showCapacityBottomSheetDialog()
        }

        binding.btnChallengeChallengePeriodDown.setOnClickListener {
            hideKeyBoard()
            showCapacityBottomSheetDialog()
        }

        binding.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnImageAddSuccess.setOnClickListener {
            imageType = ImageType.SUCCESS
            requestPermission()
        }

        binding.btnImageAddFail.setOnClickListener {
            imageType = ImageType.FAIL
            requestPermission()
        }
    }

    private fun showCapacityBottomSheetDialog() {
        val dialog = CapacityBottomSheetDialog()
        dialog.show(parentFragmentManager, "Capacity")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }

    private fun hideKeyBoard() {
        binding.etChallengeIntroduction.clearFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etChallengeAuthentication.windowToken, 0)
    }

    private fun setRecyclerView() {
        val imageListAdapter1 = ImageListAdapter()
        binding.rvSuccessImage.apply {
            adapter = imageListAdapter1
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
        }
        viewModel.successImageList.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                if (it.size >= 2) binding.btnImageAddSuccess.visibility = View.GONE
                else binding.btnImageAddSuccess.visibility = View.VISIBLE
                imageListAdapter1.submitList(it)
                Log.d("TEST image","asd")
            }
        }
        val imageListAdapter = ImageListAdapter()
        binding.rvFailImage.apply {
            adapter = imageListAdapter
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
        }

        viewModel.failImageList.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                if (it.size >= 2) binding.btnImageAddFail.visibility = View.GONE
                else binding.btnImageAddFail.visibility = View.VISIBLE
                imageListAdapter.submitList(it)
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openGallery()
            } else {
                // Handle permission denied
            }
        }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            openGallery()
        }
    }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                // Call a function to set the image from the URI to the ImageView
                setImageFromUri(selectedImageUri)
            }
        }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    private fun setImageFromUri(imageUri: Uri?) {
        try {
            if (imageUri != null) {
                if (imageType == ImageType.SUCCESS) {
                    viewModel.addImageSuccess(ImageCardModel(imageUri))
                    Log.d("TEST image", "add Image success")
                }
                else {
                    viewModel.addImageFail(ImageCardModel(imageUri))
                    Log.d("TEST image", "add Image fail")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}