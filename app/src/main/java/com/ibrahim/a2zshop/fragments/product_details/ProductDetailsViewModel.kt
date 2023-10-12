package com.ibrahim.a2zshop.fragments.product_details

import android.accounts.NetworkErrorException
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.A2ZUseCases
import com.ibrahim.a2zshop.authorization
import com.ibrahim.a2zshop.lang
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val a2ZUseCases: A2ZUseCases,
    application: Application
) :
    AndroidViewModel(application) {

    private val _favorite = MutableLiveData<Boolean>()
    val favorite: LiveData<Boolean> get() = _favorite

    private val _addedToCart = MutableLiveData<Boolean>()
    val addedToCart: LiveData<Boolean> get() = _addedToCart

    fun addOrDeleteFavorite(id: Int) {
        iconFavoriteToggle(_favorite.value!!)
        viewModelScope.launch {
            try {
                val res = withContext(Dispatchers.IO) {
                    a2ZUseCases.addOrDeleteFavorite(id, lang, authorization)
                }
                if (res.status) {
                    Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
                } else {
                    iconFavoriteToggle(_favorite.value!!)
                    Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: NetworkErrorException) {
                Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun addOrDeleteProductToCart(id: Int) {
        viewModelScope.launch {
            try {
                val res = withContext(Dispatchers.IO) {
                    a2ZUseCases.addToCart(id, lang, authorization)
                }
                if (res.status) {
                    textCartToggle(_addedToCart.value!!)
                    Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: NetworkErrorException) {
                Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun iconFavoriteToggle(inFavorite: Boolean) {
        _favorite.value = !inFavorite
    }

    fun favorite(inFavorite: Boolean) {
        _favorite.value = inFavorite
    }

    private fun textCartToggle(inCart: Boolean) {
        _addedToCart.value = !inCart
    }

    fun textCart(inCart: Boolean) {
        _addedToCart.value = inCart
    }
}