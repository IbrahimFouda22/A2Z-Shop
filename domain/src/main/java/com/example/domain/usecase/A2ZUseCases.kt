package com.example.domain.usecase

import com.example.domain.models.RegisterModel
import com.example.domain.repo.A2ZRepo

class A2ZUseCases(private val a2ZRepo: A2ZRepo) {
    suspend fun login(email: String, password: String, lang: String) =
        a2ZRepo.login(email, password, lang)

    suspend fun register(model: RegisterModel, lang: String) = a2ZRepo.register(model, lang)

    suspend fun getHomeData(lang: String, authorization: String) =
        a2ZRepo.getHomeData(lang, authorization)

    suspend fun addOrDeleteFavorite(
        id: Int,
        lang: String,
        authorization: String
    ) = a2ZRepo.addOrDeleteFavorite(id, lang, authorization)

    suspend fun searchProducts(
        text: String,
        lang: String,
        authorization: String
    ) = a2ZRepo.searchProducts(text, lang, authorization)

    suspend fun getCartData(
        lang: String,
        authorization: String
    ) = a2ZRepo.getCartData(lang, authorization)

    suspend fun addToCart(
        productId: Int,
        lang: String,
        authorization: String
    ) = a2ZRepo.addToCart(productId, lang, authorization)

    suspend fun editQty(
        id: Int,
        qty: Int,
        lang: String,
        authorization: String
    ) = a2ZRepo.editQty(id, qty, lang, authorization)

    suspend fun getCategoryData(lang: String) = a2ZRepo.getCategoryData(lang)

    suspend fun getCategoryDetails(
        id: Int,
        lang: String,
        authorization: String
    ) = a2ZRepo.getCategoryDetails(id, lang, authorization)

    suspend fun logOut(
        fcmToken: String,
        lang: String,
        authorization: String
    ) = a2ZRepo.logOut(fcmToken, lang, authorization)

    suspend fun updateProfile(
        name: String,
        email: String,
        phone: String,
        lang: String,
        authorization: String
    ) = a2ZRepo.updateProfile(name, email, phone, lang, authorization)

    suspend fun getFavorites(
        lang: String,
        authorization: String
    ) = a2ZRepo.getFavorites(lang, authorization)

    suspend fun changePass(
        currentPass: String,
        newPass: String,
        lang: String,
        authorization: String
    ) = a2ZRepo.changePass(currentPass, newPass, lang, authorization)
}