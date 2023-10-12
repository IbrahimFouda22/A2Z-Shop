package com.ibrahim.a2zshop.fragments.account

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.models.UserData
import com.example.domain.usecase.A2ZUseCases
import com.ibrahim.a2zshop.authorization
import com.ibrahim.a2zshop.lang
import com.ibrahim.a2zshop.saveUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    application: Application,
    private val a2ZUseCases: A2ZUseCases
) :
    AndroidViewModel(application) {

    private val _changeUser = MutableLiveData(false)
    val changeUser: LiveData<Boolean> get() = _changeUser

    private val _showCancel = MutableLiveData(false)
    val showCancel: LiveData<Boolean> get() = _showCancel

    private val _showEdit = MutableLiveData(true)
    val showEdit: LiveData<Boolean> get() = _showEdit

    private val _showEdit2 = MutableLiveData(false)
    val showEdit2: LiveData<Boolean> get() = _showEdit2

    private val _showConsUp = MutableLiveData(true)
    val showConsUp: LiveData<Boolean> get() = _showConsUp

    private val _showConsDown = MutableLiveData(true)
    val showConsDown: LiveData<Boolean> get() = _showConsDown

    private val _user = MutableLiveData<UserData>()
    val user: LiveData<UserData> get() = _user

    private val _moveToFavorite = MutableLiveData(false)
    val moveToFavorite: LiveData<Boolean> get() = _moveToFavorite

    private val _moveToChangePass = MutableLiveData(false)
    val moveToChangePass: LiveData<Boolean> get() = _moveToChangePass

    val edtName = MutableLiveData<String>()

    //val edtName : LiveData<String> get() = _edtName

    val edtEmail = MutableLiveData<String>()
    //val edtEmail : LiveData<String> get() = _edtEmail

    val edtPhone = MutableLiveData<String>()
    //val edtPhone : LiveData<String> get() = _edtPhone


    fun setEdit2Show(showEdit: Boolean) {
        _showEdit2.value = showEdit
    }

    fun setData(user: UserData) {
        _user.value = user
        edtName.value = user.name!!
        edtEmail.value = user.email!!
        edtPhone.value = user.phone!!
    }

    fun onClickEdit() {
        _showEdit.value = false
        _showCancel.value = true
        _showEdit2.value = false
        _showConsUp.value = true
        _showConsDown.value = false
    }

    fun onClickCancel() {
        _showEdit.value = true
        _showCancel.value = false
        _showEdit2.value = false
        _showConsUp.value = true
        _showConsDown.value = true
        setData(user.value!!)
    }

    fun onClickEdit2() {
        try {
            viewModelScope.launch {
                val res = withContext(Dispatchers.IO) {
                    a2ZUseCases.updateProfile(
                        edtName.value.toString(),
                        edtEmail.value.toString(),
                        edtPhone.value.toString(),
                        lang,
                        authorization
                    )
                }
                if (res.status) {
                    Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
                    setData(res.data)
                    saveUser(
                        getApplication(),
                        res.data.name,
                        res.data.email,
                        res.data.phone,
                        res.data.token
                    )
                    _changeUser.value = true
                } else
                    Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    fun changeUserDone() {
        _changeUser.value = false
    }

    fun moveToFavorite() {
        _moveToFavorite.value = true
    }

    fun moveToFavoriteDone() {
        _moveToFavorite.value = false
    }

    fun moveToChangePass() {
        _moveToChangePass.value = true
    }

    fun moveToChangePassDone() {
        _moveToChangePass.value = false
    }
}

