package com.ibrahim.a2zshop

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lang = getLang()
        setLocal(this, lang)
        mode = getMode()
    }

    private fun getLang(): String {
        val sharedPreferences =
            application.getSharedPreferences("MyShared", Context.MODE_PRIVATE)
        val lang = sharedPreferences.getString("lang", null)
        return if (lang == null) {
            saveLang(application, "en")
            "en"
        } else
            lang
    }

    private fun getMode(): String {
        val sharedPreferences =
            application.getSharedPreferences("MyShared", Context.MODE_PRIVATE)
        val mode = sharedPreferences.getString("mode", null)
        return if (mode == null) {
            saveMode(application, "light")
            "light"
        } else
            mode
    }
}

