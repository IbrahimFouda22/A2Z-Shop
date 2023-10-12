package com.example.domain.models

data class CategoryModel(val status: Boolean, val message: String?, val data: CategoryData)
data class CategoryData(val current_page: Int, val data: List<EntireData>)
data class EntireData(val id: Int, val name: String, val image: String)

data class CategoryDetailsModel(
    val status: Boolean,
    val message: String?,
    val data: CategoryDetailsData
)

data class CategoryDetailsData(val current_page: Int, val data: List<ProductModel>)