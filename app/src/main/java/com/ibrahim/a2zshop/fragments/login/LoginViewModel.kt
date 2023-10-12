package com.ibrahim.a2zshop.fragments.login

import android.accounts.NetworkErrorException
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.models.UserData
import com.example.domain.usecase.A2ZUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val a2ZUseCases: A2ZUseCases,
    application: Application
) : AndroidViewModel(application) {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _user = MutableLiveData<UserData>()
    val user: LiveData<UserData> get() = _user

    private val _navigateToRegister = MutableLiveData(false)
    val navigateToRegister: LiveData<Boolean> get() = _navigateToRegister

    private val _navigateToHome = MutableLiveData(false)
    val navigateToHome: LiveData<Boolean> get() = _navigateToHome

    private val _visibilityLoadingProgress = MutableLiveData(false)
    val visibilityLoadingProgress: LiveData<Boolean> get() = _visibilityLoadingProgress

    fun login() {
        _visibilityLoadingProgress.value = true
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    a2ZUseCases.login(
                        email.value.toString(),
                        password.value.toString(),
                        "en"
                    )
                }
                if (result.status) {
                    _visibilityLoadingProgress.value = false
                    _user.value = result.data
                    _navigateToHome.value = true
                } else {
                    _visibilityLoadingProgress.value = false
                    Toast.makeText(getApplication(), result.message, Toast.LENGTH_LONG).show()
                }
            } catch (e: NetworkErrorException) {
                Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun navigateToRegister() {
        _navigateToRegister.value = true
    }

    fun navigateToRegisterDone() {
        _navigateToRegister.value = false
    }

    fun navigateToHomeDone() {
        _navigateToHome.value = false
    }
}