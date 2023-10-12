package com.ibrahim.a2zshop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ibrahim.a2zshop.databinding.ItemSliderBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderProductAdapter(private val images: List<String>) :
    SliderViewAdapter<SliderProductAdapter.ViewHolder>() {
    class ViewHolder(private val itemSliderBinding: ItemSliderBinding) :
        SliderViewAdapter.ViewHolder(itemSliderBinding.root) {
        fun bind(image: String) {
            itemSliderBinding.bannerData = image
            itemSliderBinding.executePendingBindings()
        }
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        return ViewHolder(ItemSliderBinding.inflate(LayoutInflater.from(parent!!.context)))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, position: Int) {
        viewHolder!!.bind(images[position])
    }
}