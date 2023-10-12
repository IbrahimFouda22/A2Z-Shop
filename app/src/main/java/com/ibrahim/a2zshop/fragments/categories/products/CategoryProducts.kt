package com.ibrahim.a2zshop.fragments.categories.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ibrahim.a2zshop.OnClickListener
import com.ibrahim.a2zshop.R
import com.ibrahim.a2zshop.adapters.HomeAdapter
import com.ibrahim.a2zshop.databinding.FragmentCategoryProductsBinding
import com.ibrahim.a2zshop.fragments.categories.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryProducts : Fragment() {

    private val navArgs by navArgs<CategoryProductsArgs>()
    private val sharedViewModel by viewModels<CategoryViewModel>({ requireActivity() })
    private lateinit var binding: FragmentCategoryProductsBinding
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var edtSearch: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCategoryProductsBinding.inflate(layoutInflater)
        bottomNav = requireActivity().findViewById(R.id.bottomNavigation)
        edtSearch = requireActivity().findViewById(R.id.edtSearch)

        val adapter = HomeAdapter(OnClickListener({
            findNavController().navigate(
                CategoryProductsDirections.actionCategoryProductsToProductDetailsFragment(
                    it
                )
            )
        }, {
            sharedViewModel.addOrDeleteFavorite(it, navArgs.categoryId)
        }, null, null))

        binding.recyclerCategoryDetails.adapter = adapter
        adapter.submitList(null)
        sharedViewModel.categoryDetails.observe(viewLifecycleOwner) {
            adapter.submitList(it.data.data)
        }

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