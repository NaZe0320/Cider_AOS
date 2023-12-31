package com.cider.cider.presentation.register

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.cider.cider.R
import com.cider.cider.databinding.FragmentLoginBinding
import com.cider.cider.presentation.viewmodel.LoginViewModel
import com.cider.cider.utils.binding.BindingFragment
import com.cider.cider.utils.loginWithKakao
import com.cider.cider.utils.loginWithKakaoAccount
import com.cider.cider.utils.loginWithKakaoTalk
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BindingFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegisterKakao.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    // 서비스 코드에서는 간단하게 로그인 요청하고 oAuthToken 을 받아올 수 있다.
                    val oAuthToken = UserApiClient.loginWithKakao(requireContext())
                    Log.d("KaKao Test", "beanbean > $oAuthToken")

                    Log.e("Kakao Login","로그인 성공 ${oAuthToken.accessToken}")

                    viewModel.loginFirst(oAuthToken.accessToken)

                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.fl_login, RegisterFragment(), "Register")
                        addToBackStack("Register")
                        commit()
                    }

                } catch (error: Throwable) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        Log.d("KaKao Test", "사용자가 명시적으로 취소")
                    } else {
                        Log.e("KaKao Test", "인증 에러 발생", error)
                    }
                }
            }
        }
    }

}