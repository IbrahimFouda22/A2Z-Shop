package com.ibrahim.a2zshop.fragments.change_password

import android.accounts.NetworkErrorException
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
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
class ChangePasswordViewModel @Inject constructor(
    private val a2ZUseCases: A2ZUseCases,
    application: Application
) :
    AndroidViewModel(application) {

    val currentPass = MutableLiveData<String>()
    val newPass = MutableLiveData<String>()

    fun changePass() {
        if (!currentPass.value.isNullOrEmpty() && !newPass.value.isNullOrEmpty()) {
            try {
                viewModelScope.launch {
                    val res = withContext(Dispatchers.IO) {
                        a2ZUseCases.changePass(
                            currentPass.value!!, newPass.value!!, lang,
                            authorization
                        )
                    }
                    if (res.status) {
                        Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
                        currentPass.value = ""
                        newPass.value = ""
                    } else
                        Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: NetworkErrorException) {
                Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        } else
            Toast.makeText(getApplication(), "Please Enter Password", Toast.LENGTH_SHORT).show()
    }
}