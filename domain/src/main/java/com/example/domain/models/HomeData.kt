package com.example.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class HomeData(
    val status: Boolean, val data: DataObject,
)

data class DataObject(
    val banners: List<BannerModel>,
    val products: List<ProductModel>,
    val ad: String
)

data class BannerModel(
    val id: Int, val image: String
)

@Parcelize
data class ProductModel(
    val id: Int,
    val price: Double,
    val old_price: Double,
    val discount: Double,
    val image: String,
    val name: String,
    val description: String,
    val images: List<String>,
    var in_favorites: Boolean,
    var in_cart: Boolean
) : Parcelable