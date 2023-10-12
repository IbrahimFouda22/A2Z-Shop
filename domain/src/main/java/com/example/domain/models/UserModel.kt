package com.example.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class UserModel(
    val status: Boolean, val message: String, val data: UserData
)

@Parcelize
data class UserData(
    var name: String?,
    var email: String?,
    var phone: String?,
    val image: String?,
    val points: Int?,
    val credit: Int?,
    var token: String?,
    val password: String?
) : Parcelable

data class RegisterModel(
    val name: String,
    val email: String,
    val password: String,
    val phone: String
)
