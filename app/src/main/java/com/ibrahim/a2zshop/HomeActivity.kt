package com.ibrahim.a2zshop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.domain.models.UserData
import com.ibrahim.a2zshop.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val viewModel: HomeActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getParcelableExtra<UserData>("User")
        authorization = user!!.token!!
        Log.d("token", authorization)
        //val modelFactory = HomeViewModelFactory(application, lang, authorization)
        //viewModel = ViewModelProvider(this)[HomeActivityViewModel::class.java]
        viewModel.setUser(user)

        val binding = ActivityHomeBinding.inflate(layoutInflater)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.home_nav) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)
        //var text: String? = null

        //Toast.makeText(applicationContext, navHostFragment.navController.currentDestination?.id.toString(), Toast.LENGTH_SHORT).show()

        viewModel.language.observe(this) {
            setLocal(this, it)
        }

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.setText(p0.toString())
                //text = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        viewModel.logOut.observe(this) {
            if (it) {
                removeUser()
                startActivity(
                    Intent(this, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                finish()
            }
        }

        viewModel.cart.observe(this) {
            if (it.data.cart_items.isNotEmpty())
                viewModel.setBadge(it.data.cart_items.size)
            else
                viewModel.setBadge(0)
        }

        viewModel.badge.observe(this) {
            val badge = binding.bottomNavigation.getOrCreateBadge(R.id.cartFragment)
            if (it > 0) {
                badge.isVisible = true
                badge.number = it
            } else
                badge.isVisible = false
        }

        binding.edtSearch.setOnKeyListener { view, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_BACK) {
                //viewModel.setText(text!!)
                binding.edtSearch.clearFocus()
                closeKeyBoard(view)
            }
            true
        }

        binding.edtSearch.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                viewModel.navigateToSearch(true)
            else
                viewModel.navigateToSearch(false)
        }


        setContentView(binding.root)
    }

    private fun closeKeyBoard(view: View) {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    //    override fun onDestroy() {
//        super.onDestroy()
//        viewModel.logOut()
//        Log.d("token","onDestroy")
//    }
//    override fun onStop() {
//        super.onStop()
//    }
    private fun removeUser() {
        // To store values in shared preferences:
        val sharedPreferences =
            application.getSharedPreferences("MyShared", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", null)
        editor.putString("email", null)
        editor.putString("phone", null)
        editor.putString("token", null)
        editor.apply()
    }
}