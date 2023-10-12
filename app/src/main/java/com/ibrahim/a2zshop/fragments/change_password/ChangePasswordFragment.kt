package com.ibrahim.a2zshop.fragments.change_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ibrahim.a2zshop.R
import com.ibrahim.a2zshop.databinding.FragmentChangePasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {

    val viewModel by viewModels<ChangePasswordViewModel>({ this })
    private lateinit var binding: FragmentChangePasswordBinding
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var edtSearch: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(layoutInflater)
        bottomNav = requireActivity().findViewById(R.id.bottomNavigation)
        edtSearch = requireActivity().findViewById(R.id.edtSearch)

        binding.lifecycleOwner = this
        binding.model = viewModel

        return binding.root
    }

    override fun onStart() {
        super.onStart()
//        edtSearch.text.clear()
//        edtSearch.clearFocus()
        edtSearch.visibility = View.GONE
        bottomNav.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        edtSearch.visibility = View.VISIBLE
        bottomNav.visibility = View.VISIBLE
    }
}