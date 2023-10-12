package com.ibrahim.a2zshop.fragments.product_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ibrahim.a2zshop.HomeActivityViewModel
import com.ibrahim.a2zshop.R
import com.ibrahim.a2zshop.adapters.SliderProductAdapter
import com.ibrahim.a2zshop.databinding.FragmentProductDetailsBinding
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private val navArgs by navArgs<ProductDetailsFragmentArgs>()
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var edtSearch: EditText
    private val sharedViewModel by viewModels<HomeActivityViewModel>({ requireActivity() })
    private val viewModel by viewModels<ProductDetailsViewModel>({ this })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentProductDetailsBinding.inflate(layoutInflater)
        bottomNav = requireActivity().findViewById(R.id.bottomNavigation)
        edtSearch = requireActivity().findViewById(R.id.edtSearch)
        val productModel = navArgs.product
        viewModel.favorite(productModel.in_favorites)
        viewModel.textCart(productModel.in_cart)
        //Toast.makeText(activity, requireActivity().supportFragmentManager.backStackEntryCount.toString(), Toast.LENGTH_SHORT).show()
        // binding.productModel = productModel
        binding.sliderProductDetails.setIndicatorAnimation(IndicatorAnimationType.WORM)
        binding.sliderProductDetails.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
        binding.sliderProductDetails.setSliderAdapter(SliderProductAdapter(navArgs.product.images))
        //binding.sliderProductDetails.setSliderAdapter(SliderProductAdapter(productModel.images))

//        sharedViewModel.navigateToSearch.observe(viewLifecycleOwner){
//            if(it) {
//                findNavController().navigate(ProductDetailsFragmentDirections.actionProductDetailsFragmentToSearchFragment())
//                sharedViewModel.navigateToSearchDone()
//            }
//        }
        binding.btnAddToCart.setOnClickListener {
            viewModel.addOrDeleteProductToCart(productModel.id)
        }
        binding.imgButtonProductDetails.setOnClickListener {
            viewModel.addOrDeleteFavorite(productModel.id)
        }
        viewModel.addedToCart.observe(viewLifecycleOwner) {
            productModel.in_cart = it
            binding.productModel = productModel
            sharedViewModel.getCartData()
        }
        viewModel.favorite.observe(viewLifecycleOwner) {
            productModel.in_favorites = it
            binding.productModel = productModel
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