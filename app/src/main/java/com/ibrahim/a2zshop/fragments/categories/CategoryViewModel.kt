package com.ibrahim.a2zshop.fragments.categories

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.models.CategoryDetailsModel
import com.example.domain.models.CategoryModel
import com.example.domain.usecase.A2ZUseCases
import com.ibrahim.a2zshop.authorization
import com.ibrahim.a2zshop.lang
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val a2ZUseCases: A2ZUseCases,
    application: Application
) : AndroidViewModel(application) {

    private val _categoriesItems = MutableLiveData<CategoryModel>()
    val categoriesItems: LiveData<CategoryModel> get() = _categoriesItems

    private val _categoryDetails = MutableLiveData<CategoryDetailsModel>()
    val categoryDetails: LiveData<CategoryDetailsModel> get() = _categoryDetails

    init {
        getCategoryData()
    }

    private fun getCategoryData() {
        viewModelScope.launch {
            try {
                val res = withContext(Dispatchers.IO) {
                    a2ZUseCases.getCategoryData(lang)
                }
                if (res.status)
                    _categoriesItems.value = res
                //Log.d("categoryData",res.data.data[0].name + res.data.data[1].name)
                else
                    Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun getCategoryDetails(id: Int) {
        viewModelScope.launch {
            try {
                val res = withContext(Dispatchers.IO) {
                    a2ZUseCases.getCategoryDetails(id, lang, authorization)
                }
                if (res.status)
                    _categoryDetails.value = res
                else
                    Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun addOrDeleteFavorite(id: Int, categoryId: Int) {
        viewModelScope.launch {
            try {
                val res = withContext(Dispatchers.IO) {
                    a2ZUseCases.addOrDeleteFavorite(id, lang, authorization)
                }
                if (res.status) {
                    Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
                } else {
                    getCategoryDetails(categoryId)
                    Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}