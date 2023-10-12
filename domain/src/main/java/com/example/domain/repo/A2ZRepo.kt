package com.example.domain.repo

import com.example.domain.models.AddOrDeleteFavoriteModel
import com.example.domain.models.CartModel
import com.example.domain.models.CartModelAddToCart
import com.example.domain.models.CategoryDetailsModel
import com.example.domain.models.CategoryModel
import com.example.domain.models.EditQtyModel
import com.example.domain.models.FavoritesModel
import com.example.domain.models.HomeData
import com.example.domain.models.LogoutModel
import com.example.domain.models.RegisterModel
import com.example.domain.models.SearchModel
import com.example.domain.models.UserModel

interface A2ZRepo {

    suspend fun login(email: String, password: String, lang: String): UserModel

    suspend fun register(model: RegisterModel, lang: String): UserModel

    suspend fun getHomeData(lang: String, authorization: String): HomeData

    suspend fun addOrDeleteFavorite(
        id: Int,
        lang: String,
        authorization: String
    ): AddOrDeleteFavoriteModel

    suspend fun searchProducts(
        text: String,
        lang: String,
        authorization: String
    ): SearchModel

    suspend fun getCartData(
        lang: String,
        authorization: String
    ): CartModel

    suspend fun addToCart(
        productId: Int,
        lang: String,
        authorization: String
    ): CartModelAddToCart

    suspend fun editQty(
        id: Int,
        qty: Int,
        lang: String,
        authorization: String
    ): EditQtyModel

    suspend fun getCategoryData(lang: String): CategoryModel

    suspend fun getCategoryDetails(
        id: Int,
        lang: String,
        authorization: String
    ): CategoryDetailsModel

    suspend fun logOut(
        fcmToken: String,
        lang: String,
        authorization: String
    ): LogoutModel

    suspend fun updateProfile(
        name: String,
        email: String,
        phone: String,
        lang: String,
        authorization: String
    ): UserModel

    suspend fun getFavorites(
        lang: String,
        authorization: String
    ): FavoritesModel

    suspend fun changePass(
        currentPass: String,
        newPass: String,
        lang: String,
        authorization: String
    ): LogoutModel
}