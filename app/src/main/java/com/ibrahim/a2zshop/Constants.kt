package com.ibrahim.a2zshop

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

var lang = "en"
var mode = "dark"
lateinit var authorization: String
val nf: NumberFormat = NumberFormat.getInstance(Locale("ar", "EG")) //or "nb","No" - for Norway

fun setLocal(activity: Activity, lang: String) {
    val locale = Locale(lang)
    Locale.setDefault(locale)
    val resources = activity.resources
    val config = resources.configuration
    config.setLocale(locale)
    resources.updateConfiguration(config, resources.displayMetrics)

}

fun saveLang(application: Application, lang: String) {
    val sharedPreferences =
        application.getSharedPreferences("MyShared", AppCompatActivity.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("lang", lang)
    editor.apply()
}

fun saveMode(application: Application, mode: String) {
    val sharedPreferences =
        application.getSharedPreferences("MyShared", AppCompatActivity.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("mode", mode)
    editor.apply()
}

fun saveUser(
    application: Application,
    name: String?,
    email: String?,
    phone: String?,
    token: String?
) {
    // To store values in shared preferences:
    val sharedPreferences =
        application.getSharedPreferences("MyShared", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("name", name)
    editor.putString("email", email)
    editor.putString("phone", phone)
    editor.putString("token", token)
    editor.apply()
}
/*sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}

suspend fun fetchData(): Result<Data> {
    return try {
        val response = apiService.getData()
        Result.Success(response.body()!!)
    } catch (e: Exception) {
        Result.Error(e)
    }
}*/
