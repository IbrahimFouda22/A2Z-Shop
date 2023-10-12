package com.ibrahim.a2zshop.fragments.home

import android.accounts.NetworkErrorException
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.models.BannerModel
import com.example.domain.models.ProductModel
import com.example.domain.usecase.A2ZUseCases
import com.ibrahim.a2zshop.authorization
import com.ibrahim.a2zshop.lang
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val a2ZUseCases: A2ZUseCases,
    application: Application
) : AndroidViewModel(application) {

    private val _bannerList = MutableLiveData<List<BannerModel>>()
    val bannerList: LiveData<List<BannerModel>> get() = _bannerList

    private val _productList = MutableLiveData<List<ProductModel>>()
    val productList: LiveData<List<ProductModel>> get() = _productList


    init {
        getHomeData()
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
                    getHomeData()
                    Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: NetworkErrorException) {
                Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun getHomeData() {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.Main) {
                    a2ZUseCases.getHomeData(lang, authorization)
                }
                _bannerList.value = data.data.banners
                _productList.value = data.data.products
            } catch (e: NetworkErrorException) {
                Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}