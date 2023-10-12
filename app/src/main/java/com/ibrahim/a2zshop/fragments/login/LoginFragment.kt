package com.ibrahim.a2zshop.fragments.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ibrahim.a2zshop.HomeActivity
import com.ibrahim.a2zshop.R
import com.ibrahim.a2zshop.databinding.FragmentLoginBinding
import com.ibrahim.a2zshop.saveUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>({ this })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.edtYourEmailLogin.setOnKeyListener { view, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_BACK) {
                //viewModel.setText(text!!)
                binding.edtYourEmailLogin.clearFocus()
                closeKeyBoard(view)
            }
            true
        }

        loginViewModel.navigateToRegister.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
                loginViewModel.navigateToRegisterDone()
            }
        }

        loginViewModel.visibilityLoadingProgress.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingWheel.visibility = View.VISIBLE
                requireActivity().window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
            } else {
                binding.loadingWheel.visibility = View.GONE
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        }

        loginViewModel.navigateToHome.observe(viewLifecycleOwner) {
            if (it) {
//                val intent = Intent(requireActivity(), HomeActivity::class.java)
//                intent.putExtra("User",loginViewModel.user.value)
                val user = loginViewModel.user.value
                saveUser(
                    requireActivity().application,
                    user!!.name,
                    user.email,
                    user.phone,
                    user.token
                )
                startActivity(
                    Intent(
                        requireActivity(),
                        HomeActivity::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("User", user)
                )
                requireActivity().finish()
                loginViewModel.navigateToHomeDone()
            }
        }
        return binding.root

    }

    private fun closeKeyBoard(view: View) {
        val imm: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}