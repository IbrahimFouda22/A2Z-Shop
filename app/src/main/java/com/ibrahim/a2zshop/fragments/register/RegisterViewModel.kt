package com.ibrahim.a2zshop.fragments.register

import android.accounts.NetworkErrorException
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.models.RegisterModel
import com.example.domain.usecase.A2ZUseCases
import com.ibrahim.a2zshop.lang
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val a2ZUseCases: A2ZUseCases,
    application: Application
) : AndroidViewModel(application) {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val name = MutableLiveData<String>()

    //    private val _register = MutableLiveData(false)
//    val register : LiveData<Boolean> get() = _register
//
    private val _navigateToLogin = MutableLiveData(false)
    val navigateToLogin: LiveData<Boolean> get() = _navigateToLogin

    private val _visibilityLoadingProgress = MutableLiveData(false)
    val visibilityLoadingProgress: LiveData<Boolean> get() = _visibilityLoadingProgress

    fun register() {
        _visibilityLoadingProgress.value = true
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    a2ZUseCases.register(
                        RegisterModel(
                            name.value.toString(),
                            email.value.toString(),
                            password.value.toString(),
                            phone.value.toString()
                        ), lang
                    )
                }
                if (result.status) {
                    _visibilityLoadingProgress.value = false
                    Toast.makeText(getApplication(), result.message, Toast.LENGTH_LONG).show()
                    email.value = ""
                    name.value = ""
                    password.value = ""
                    phone.value = ""
                } else {
                    _visibilityLoadingProgress.value = false
                    Toast.makeText(getApplication(), result.message, Toast.LENGTH_LONG).show()
                }
            } catch (e: NetworkErrorException) {
                _visibilityLoadingProgress.value = false
                Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun navigateToLogin() {
        _navigateToLogin.value = true
    }

    fun navigateToLoginDone() {
        _navigateToLogin.value = false
    }
}