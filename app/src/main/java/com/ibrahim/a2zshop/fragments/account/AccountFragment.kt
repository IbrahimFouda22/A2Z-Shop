package com.ibrahim.a2zshop.fragments.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.*
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ibrahim.a2zshop.HomeActivityViewModel
import com.ibrahim.a2zshop.MainActivity
import com.ibrahim.a2zshop.R
import com.ibrahim.a2zshop.databinding.FragmentAccountBinding
import com.ibrahim.a2zshop.lang
import com.ibrahim.a2zshop.saveLang
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment() {
    private val sharedViewModel by viewModels<HomeActivityViewModel>({ requireActivity() })
    private val viewModel by viewModels<AccountViewModel>({ this })
    private lateinit var binding: FragmentAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.user = viewModel

        sharedViewModel.language.observe(viewLifecycleOwner) {
            saveLang(requireActivity().application, it)
            if (it == "ar")
                binding.spinnerLang.setSelection(1)
            else
                binding.spinnerLang.setSelection(0)

            if (lang != it) {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }
            lang = it
        }
        viewModel.showCancel.observe(viewLifecycleOwner) {
            enabled(binding.btnCancelAccount, it)
        }
        viewModel.showEdit.observe(viewLifecycleOwner) {
            enabled(binding.btnChange, it)
        }
        viewModel.showEdit2.observe(viewLifecycleOwner) {
            enabled(binding.btnChange2, it)
        }
        viewModel.showConsUp.observe(viewLifecycleOwner) {
            enabled(binding.consUP, it)
        }
        viewModel.showConsDown.observe(viewLifecycleOwner) {
            enabled(binding.consDown, it)
            enabled(binding.spinnerLang, it)
            enabled(binding.btnLogOut, it)
            enabled(binding.btnFavorite, it)
            enabled(binding.btnChangePassword, it)
            enableEditTexts(binding.edtEmailAccount, !it)
            enableEditTexts(binding.edtNameAccount, !it)
            enableEditTexts(binding.edtPhoneAccount, !it)
            //binding.consDown.isClickable = false
        }

        sharedViewModel.user.observe(viewLifecycleOwner) {
            viewModel.setData(it)
        }

        viewModel.changeUser.observe(viewLifecycleOwner) {
            if (it) {
                sharedViewModel.setUser(viewModel.user.value!!)
                viewModel.changeUserDone()
            }
        }

        viewModel.edtPhone.observe(viewLifecycleOwner) {
            if (it != viewModel.user.value!!.phone)
                viewModel.setEdit2Show(true)
            else
                viewModel.setEdit2Show(false)
        }
        viewModel.edtEmail.observe(viewLifecycleOwner) {
            if (it != viewModel.user.value!!.email)
                viewModel.setEdit2Show(true)
            else
                viewModel.setEdit2Show(false)
        }
        viewModel.edtName.observe(viewLifecycleOwner) {
            if (it != viewModel.user.value!!.name)
                viewModel.setEdit2Show(true)
            else
                viewModel.setEdit2Show(false)
        }


        binding.btnLogOut.setOnClickListener {
            sharedViewModel.logOut()
        }
        sharedViewModel.navigateToSearch.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToSearchFragment())
                sharedViewModel.navigateToSearchDone()
            }
        }

        val adapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.languages,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerLang.adapter = adapter
        binding.spinnerLang.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0)
                    sharedViewModel.setLang("en")
                else
                    sharedViewModel.setLang("ar")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.btnFavorite.setOnClickListener {
            viewModel.moveToFavorite()
        }

        viewModel.moveToFavorite.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToFavoriteFragment())
                viewModel.moveToFavoriteDone()
            }
        }

        binding.btnChangePassword.setOnClickListener {
            viewModel.moveToChangePass()
        }

        viewModel.moveToChangePass.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToChangePasswordFragment())
                viewModel.moveToChangePassDone()
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val edtSearch = requireActivity().findViewById<EditText>(R.id.edtSearch)
        edtSearch.text.clear()
        val btnCancel = requireActivity().findViewById<Button>(R.id.btnCancel)
        btnCancel.visibility = View.GONE

    }

    private fun enabled(view: View, boolean: Boolean) {
        if (boolean) {
            view.isEnabled = true
            view.alpha = 1f
        } else {
            view.isEnabled = false
            view.alpha = 0.25f
        }
    }

    private fun enableEditTexts(view: View, boolean: Boolean) {
        view.isEnabled = boolean
    }
}

