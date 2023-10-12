package com.example.domain.models

data class SearchModel(val status: Boolean, val data: SearchModelData)

data class SearchModelData(val current_page: Int, val data: List<ProductModel>)

data class SearchData(
    val id: Int,
    val price: Double,
    val image: String,
    val name: String,
    val description: String,
    val images: List<String>,
    val in_favorites: Boolean,
    val in_cart: Boolean
)
