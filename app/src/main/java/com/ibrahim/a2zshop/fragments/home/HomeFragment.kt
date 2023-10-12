package com.ibrahim.a2zshop.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ibrahim.a2zshop.HomeActivityViewModel
import com.ibrahim.a2zshop.OnClickListener
import com.ibrahim.a2zshop.R
import com.ibrahim.a2zshop.adapters.HomeAdapter
import com.ibrahim.a2zshop.adapters.SliderAdapter
import com.ibrahim.a2zshop.databinding.FragmentHomeBinding
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    //    lateinit var binding : FragmentHomeBinding
    private val sharedViewModel by viewModels<HomeActivityViewModel>({ requireActivity() })
    private val viewModel by viewModels<HomeViewModel>({ this })
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentHomeBinding.inflate(inflater)
        //requireActivity().supportFragmentManager.popBackStack("HomeFragment",FragmentManager.POP_BACK_STACK_INCLUSIVE)
        viewModel.getHomeData()

        sharedViewModel.navigateToSearch.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
                sharedViewModel.navigateToSearchDone()
            }
        }
        viewModel.bannerList.observe(viewLifecycleOwner) {
            val adapter = SliderAdapter(it)
            binding.sliderViewImage.setSliderAdapter(adapter)
            binding.sliderViewImage.setIndicatorAnimation(IndicatorAnimationType.WORM)
            binding.sliderViewImage.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
            binding.sliderViewImage.startAutoCycle()
        }

        val adapter = HomeAdapter(
            OnClickListener({
                //requireActivity().supportFragmentManager.beginTransaction().addToBackStack("ProductDetailsFragment").commit()
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(
                        it
                    )
                )
                //requireActivity().supportFragmentManager.beginTransaction().replace(R.id.home_nav,ProductDetailsFragment(it)).addToBackStack("ProductDetailsFragment").commit()
            }, {
                viewModel.addOrDeleteFavorite(it)
            }, null, null)
        )


        binding.recyclerHome.adapter = adapter
        viewModel.productList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
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