package com.ibrahim.a2zshop.fragments.cart

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ibrahim.a2zshop.HomeActivityViewModel
import com.ibrahim.a2zshop.OnClickListener
import com.ibrahim.a2zshop.R
import com.ibrahim.a2zshop.adapters.CartAdapter
import com.ibrahim.a2zshop.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var viewModel: CartViewModel
    private val sharedViewModel by viewModels<HomeActivityViewModel>({ requireActivity() })

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentCartBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this)[CartViewModel::class.java]

        viewModel.getCartData()

        sharedViewModel.navigateToSearch.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(CartFragmentDirections.actionCartFragmentToSearchFragment())
                sharedViewModel.navigateToSearchDone()
            }
        }
        //binding.recyclerCart.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        val cartAdapter = CartAdapter(OnClickListener({

        }, {
            viewModel.addOrDeleteFavorite(it)
        }, {
            viewModel.addOrDeleteProductToCart(it)
        }, { id, qty ->
            viewModel.editQty(id, qty)
        }))
        viewModel.cartData.observe(viewLifecycleOwner) {
            cartAdapter.setData(it)
            binding.recyclerCart.adapter = cartAdapter
            sharedViewModel.setBadge(it.size)
            //Log.d("cartData", it.size.toString())
            //cartAdapter.notifyDataSetChanged()
            //Log.d("cartNum",it[0].productModel.image)
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