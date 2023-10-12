package com.example.domain.models

data class FavoritesModel(
    val status: Boolean, val message: String, val data: FavoritesData
)

data class FavoritesData(val data: List<Data>)
data class Data(val id: Int, val product: ProductModel)

data class AddOrDeleteFavoriteModel(
    val status: Boolean, val message: String
)
