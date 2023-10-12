package com.ibrahim.a2zshop

import android.accounts.NetworkErrorException
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.domain.models.CartModel
import com.example.domain.models.ProductModel
import com.example.domain.models.UserData
import com.example.domain.usecase.A2ZUseCases
//import com.ibrahim.a2zshop.models.CartModel
//import com.ibrahim.a2zshop.models.ProductModel
//import com.ibrahim.a2zshop.models.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeActivityViewModel @Inject constructor(
    private val a2ZUseCases: A2ZUseCases,
    application: Application
) :
    AndroidViewModel(application) {

    private val _navigateToSearch = MutableLiveData(false)
    val navigateToSearch: LiveData<Boolean> get() = _navigateToSearch

    private val _user = MutableLiveData<UserData>()
    val user: LiveData<UserData> get() = _user

    private val _text = MutableLiveData("")
    val text: LiveData<String> get() = _text

    private val _products = MutableLiveData<List<ProductModel>>()
    val products: LiveData<List<ProductModel>> get() = _products

    private val _cart = MutableLiveData<CartModel>()
    val cart: LiveData<CartModel> get() = _cart

    private val _badge = MutableLiveData<Int>()
    val badge: LiveData<Int> get() = _badge

    private val _logOut = MutableLiveData(false)
    val logOut: LiveData<Boolean> get() = _logOut

    private val _language = MutableLiveData(lang)
    val language: LiveData<String> get() = _language


    init {
        getCartData()
    }

    fun navigateToSearch(boolean: Boolean) {
        _navigateToSearch.value = boolean
    }

    fun navigateToSearchDone() {
        _navigateToSearch.value = false
    }

    fun search(text: String) {
        if (text.isNotEmpty()) {
            try {
                viewModelScope.launch {
                    val result = withContext(Dispatchers.IO) {
                        a2ZUseCases.searchProducts(text, lang, authorization)
                    }
                    _products.value = result.data.data
                }
            } catch (e: NetworkErrorException) {
                Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun setText(text: String) {
        _text.value = text
    }

    fun addOrDeleteFavorite(id: Int) {
        viewModelScope.launch {
            try {
                val res = withContext(Dispatchers.IO) {
                    a2ZUseCases.addOrDeleteFavorite(id, lang, authorization)
                }
                if (res.status) {
                    Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
                } else {
                    setText(_text.value.toString())
                    Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: NetworkErrorException) {
                Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

//    fun addOrDeleteProductToCart(id:Int){
//        viewModelScope.launch {
//            val res = withContext(Dispatchers.IO){
//                A2ZApi.a2ZApiService.addToCart(id)
//            }
//            if (res.status)
//            {
//                Toast.makeText(application.applicationContext, res.message, Toast.LENGTH_SHORT).show()
//            }
//            else {
//                Toast.makeText(application.applicationContext, res.message, Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    fun getCartData() {
        viewModelScope.launch {
            try {
                val res = withContext(Dispatchers.IO) {
                    a2ZUseCases.getCartData(lang, authorization)
                }
                if (res.status)
                    _cart.value = res
                else
                    Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
            } catch (e: NetworkErrorException) {
                Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    fun setBadge(badgeNumber: Int) {
        _badge.value = badgeNumber
    }

    fun setUser(userData: UserData) {
        _user.value = userData
    }

    fun logOut() {
        viewModelScope.launch {
            try {
                val res = withContext(Dispatchers.IO) {
                    a2ZUseCases.logOut("SomeFcmToken", lang, authorization)
                }
                if (res.status)
                    _logOut.value = true
                else
                    Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
            } catch (e: NetworkErrorException) {
                Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun setLang(lang: String) {
        _language.value = lang
    }
}

