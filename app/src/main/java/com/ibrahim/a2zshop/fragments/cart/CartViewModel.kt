package com.ibrahim.a2zshop.fragments.cart

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.CartItems
import com.example.domain.usecase.A2ZUseCases
import com.ibrahim.a2zshop.authorization
import com.ibrahim.a2zshop.lang
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val application: Application,
    private val a2ZUseCases: A2ZUseCases,
) :
    ViewModel() {
    private val _cartData = MutableLiveData<List<CartItems>>()
    val cartData: LiveData<List<CartItems>> get() = _cartData

    init {
        getCartData()
    }


    fun getCartData() {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    a2ZUseCases.getCartData(lang, authorization)
                }
                if (result.status) {
                    _cartData.value = result.data.cart_items
                } else {
                    Toast.makeText(
                        application.applicationContext,
                        result.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    application.applicationContext,
                    "No internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    fun addOrDeleteFavorite(id: Int) {
        viewModelScope.launch {
            try {
                val res = withContext(Dispatchers.IO) {
                    a2ZUseCases.addOrDeleteFavorite(id, lang, authorization)
                }
                if (res.status) {
                    Toast.makeText(application.applicationContext, res.message, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    getCartData()
                    Toast.makeText(application.applicationContext, res.message, Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    application.applicationContext,
                    "No internet connection",
                    Toast.LENGTH_SHORT
                ).show()
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
                    Toast.makeText(application.applicationContext, res.message, Toast.LENGTH_SHORT)
                        .show()
                    getCartData()
                } else {
                    Toast.makeText(application.applicationContext, res.message, Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    application.applicationContext,
                    "No internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun editQty(id: Int, qty: Int) {
        viewModelScope.launch {
            try {
                val res = withContext(Dispatchers.IO) {
                    a2ZUseCases.editQty(id, qty, lang, authorization)
                }
                if (res.status) {
                    Toast.makeText(application.applicationContext, res.message, Toast.LENGTH_SHORT)
                        .show()
                    getCartData()
                } else {
                    Toast.makeText(application.applicationContext, res.message, Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    application.applicationContext,
                    "No internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}