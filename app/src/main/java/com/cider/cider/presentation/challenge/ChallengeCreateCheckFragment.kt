package com.cider.cider.presentation.challenge

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.cider.cider.R
import com.cider.cider.databinding.FragmentChallengeCreateCheckBinding
import com.cider.cider.databinding.FragmentChallengeCreateCompleteBinding
import com.cider.cider.databinding.FragmentChallengeCreateDetailBinding
import com.cider.cider.databinding.FragmentChallengeCreateSelectBinding
import com.cider.cider.utils.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeCreateCheckFragment: BindingFragment<FragmentChallengeCreateCheckBinding>(R.layout.fragment_challenge_create_check) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButton()
    }

    private fun setButton() {
        binding.btnChallengeCheck.setOnClickListener {
            findNavController().navigate(
                R.id.action_challengeCreateCheckFragment_to_challengeCreateCompleteFragment
            )
        }
        binding.btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController().popBackStack()
    }
}