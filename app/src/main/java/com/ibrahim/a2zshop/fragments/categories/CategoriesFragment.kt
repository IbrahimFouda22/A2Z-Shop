package com.ibrahim.a2zshop.fragments.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ibrahim.a2zshop.HomeActivityViewModel
import com.ibrahim.a2zshop.R
import com.ibrahim.a2zshop.adapters.CategoriesAdapter
import com.ibrahim.a2zshop.adapters.OnClickListenerCategory
import com.ibrahim.a2zshop.databinding.FragmentCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private val viewModel by viewModels<CategoryViewModel>({ requireActivity() })
    private val sharedViewModel by viewModels<HomeActivityViewModel>({ requireActivity() })
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //requireActivity().supportFragmentManager.popBackStack(R.id.productDetailsFragment,FragmentManager.POP_BACK_STACK_INCLUSIVE)
        binding = FragmentCategoriesBinding.inflate(layoutInflater)

        sharedViewModel.navigateToSearch.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(CategoriesFragmentDirections.actionCategoriesFragmentToSearchFragment())
                sharedViewModel.navigateToSearchDone()
            }
        }

        val adapter = CategoriesAdapter(OnClickListenerCategory {
            viewModel.getCategoryDetails(it)
            findNavController().navigate(
                CategoriesFragmentDirections.actionCategoriesFragmentToCategoryProducts(
                    it
                )
            )
        })

        viewModel.categoriesItems.observe(viewLifecycleOwner) {
            adapter.setData(it.data.data)
            binding.recyclerCategory.adapter = adapter
            //Log.d("categoryData",it.data.data[0].name + it.data.data[1].name)
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
}