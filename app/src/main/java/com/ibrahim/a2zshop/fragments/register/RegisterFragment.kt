package com.ibrahim.a2zshop.fragments.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ibrahim.a2zshop.R
import com.ibrahim.a2zshop.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>({ this })
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.registerViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.visibilityLoadingProgress.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingWheelRegister.visibility = View.VISIBLE
                requireActivity().window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
            } else {
                binding.loadingWheelRegister.visibility = View.GONE
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        }

        viewModel.navigateToLogin.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                viewModel.navigateToLoginDone()
            }
        }
        return binding.root
    }

}