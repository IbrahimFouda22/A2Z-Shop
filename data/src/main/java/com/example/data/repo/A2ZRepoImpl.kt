package com.example.data.repo

import com.example.data.remote.ApiService
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
import com.example.domain.repo.A2ZRepo

class A2ZRepoImpl(private val apiService: ApiService) : A2ZRepo {
    override suspend fun login(email: String, password: String, lang: String): UserModel =
        apiService.login(email, password, lang)

    override suspend fun register(model: RegisterModel, lang: String): UserModel =
        apiService.register(model, lang)

    override suspend fun getHomeData(lang: String, authorization: String): HomeData =
        apiService.getHomeData(lang, authorization)

    override suspend fun addOrDeleteFavorite(
        id: Int,
        lang: String,
        authorization: String
    ): AddOrDeleteFavoriteModel = apiService.addOrDeleteFavorite(id, lang, authorization)

    override suspend fun searchProducts(
        text: String,
        lang: String,
        authorization: String
    ): SearchModel = apiService.searchProducts(text, lang, authorization)

    override suspend fun getCartData(lang: String, authorization: String): CartModel =
        apiService.getCartData(lang, authorization)

    override suspend fun addToCart(
        productId: Int,
        lang: String,
        authorization: String
    ): CartModelAddToCart = apiService.addToCart(productId, lang, authorization)

    override suspend fun editQty(
        id: Int,
        qty: Int,
        lang: String,
        authorization: String
    ): EditQtyModel =
        apiService.editQty(id, qty, lang, authorization)

    override suspend fun getCategoryData(lang: String): CategoryModel =
        apiService.getCategoryData(lang)

    override suspend fun getCategoryDetails(
        id: Int,
        lang: String,
        authorization: String
    ): CategoryDetailsModel = apiService.getCategoryDetails(id, lang, authorization)

    override suspend fun logOut(
        fcmToken: String,
        lang: String,
        authorization: String
    ): LogoutModel =
        apiService.logOut(fcmToken, lang, authorization)

    override suspend fun updateProfile(
        name: String,
        email: String,
        phone: String,
        lang: String,
        authorization: String
    ): UserModel = apiService.updateProfile(name, email, phone, lang, authorization)

    override suspend fun getFavorites(lang: String, authorization: String): FavoritesModel =
        apiService.getFavorites(lang, authorization)

    override suspend fun changePass(
        currentPass: String,
        newPass: String,
        lang: String,
        authorization: String
    ): LogoutModel = apiService.changePass(currentPass, newPass, lang, authorization)
}