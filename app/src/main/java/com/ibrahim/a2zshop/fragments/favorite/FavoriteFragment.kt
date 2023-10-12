package com.ibrahim.a2zshop.fragments.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ibrahim.a2zshop.OnClickListener
import com.ibrahim.a2zshop.R
import com.ibrahim.a2zshop.adapters.FavoriteAdapter
import com.ibrahim.a2zshop.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var edtSearch: EditText
    private val viewModel by viewModels<FavoriteViewModel>({ this })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val binding = FragmentFavoriteBinding.inflate(layoutInflater)
        bottomNav = requireActivity().findViewById(R.id.bottomNavigation)
        edtSearch = requireActivity().findViewById(R.id.edtSearch)

        val adapter = FavoriteAdapter(null, OnClickListener({
            null
        }, {
            viewModel.addOrDeleteFavorite(it)
        }, null, null))

        binding.recyclerFavorite.adapter = adapter
        viewModel.products.observe(viewLifecycleOwner) {
            adapter.setProducts(it)
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