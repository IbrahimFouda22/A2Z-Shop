package com.ibrahim.a2zshop.fragments.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ibrahim.a2zshop.HomeActivityViewModel
import com.ibrahim.a2zshop.OnClickListener
import com.ibrahim.a2zshop.R
import com.ibrahim.a2zshop.adapters.SearchAdapter
import com.ibrahim.a2zshop.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private val sharedViewModel by viewModels<HomeActivityViewModel>({ requireActivity() })
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var textA2Z: TextView
    private lateinit var btnCancel: Button
    private lateinit var edtSearch: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentSearchBinding.inflate(layoutInflater)
        bottomNav = requireActivity().findViewById(R.id.bottomNavigation)
        textA2Z = requireActivity().findViewById(R.id.txtA2Z)
        btnCancel = requireActivity().findViewById(R.id.btnCancel)
        edtSearch = requireActivity().findViewById(R.id.edtSearch)
        sharedViewModel.text.observe(viewLifecycleOwner) {
            sharedViewModel.search(it)
        }

        val adapter = SearchAdapter(null, OnClickListener({
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToProductDetailsFragment(
                    it
                )
            )
        }, {
            sharedViewModel.addOrDeleteFavorite(it)
        }, null, null))

        binding.recyclerSearch.adapter = adapter
        sharedViewModel.products.observe(viewLifecycleOwner) {
            //Log.d("SearchData",it[0].id.toString()+it[0].name)
            adapter.setProducts(it)
        }
        btnCancel.setOnClickListener {
            cancel(it)
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        bottomNav.visibility = View.GONE
        textA2Z.visibility = View.GONE
        btnCancel.visibility = View.VISIBLE
    }

    override fun onStop() {
        super.onStop()
        bottomNav.visibility = View.VISIBLE
        textA2Z.visibility = View.VISIBLE
        btnCancel.visibility = View.GONE
    }

    private fun cancel(view: View) {
        bottomNav.visibility = View.VISIBLE
        textA2Z.visibility = View.VISIBLE
        btnCancel.visibility = View.GONE
        edtSearch.clearFocus()
        closeKeyBoard(view)
        requireActivity().onBackPressed()
        //parentFragmentManager.popBackStack()
    }

    private fun closeKeyBoard(view: View) {
        val imm: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}

//button.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        if (getFragmentManager().getBackStackEntryCount() > 0) {
//            getFragmentManager().popBackStack();
//        }
//    }
//});
