package com.ibrahim.a2zshop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.domain.models.UserData

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)
        btnLogin.setOnClickListener {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        }
        btnRegister.setOnClickListener {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToRegisterFragment())
        }
        return view
    }

    private fun retrieveUser(): UserData? {
        // To retrieve values from shared preferences:
        val sharedPreferences =
            requireActivity().application.getSharedPreferences("MyShared", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name", null)
        Log.d("token", name.toString())
        return if (name == null)
            null
        else {
            UserData(
                sharedPreferences.getString("name", ""),
                sharedPreferences.getString("email", ""),
                sharedPreferences.getString("phone", ""),
                null,
                null,
                null,
                sharedPreferences.getString("token", ""),
                null
            )
        }
    }

    override fun onStart() {
        super.onStart()
        val user = retrieveUser()
        Log.d("token", user.toString())
        if (user != null) {
            startActivity(
                Intent(
                    requireActivity(),
                    HomeActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("User", user)
            )
            requireActivity().finish()
        }
    }
}