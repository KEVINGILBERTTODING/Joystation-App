package com.example.mvvm_kotlin.features.auth.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mvvm_kotlin.R
import com.example.mvvm_kotlin.databinding.ActivityAuthActivitiesBinding
import com.example.mvvm_kotlin.features.auth.view.fragments.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthActivitiesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentTransaction(LoginFragment())
    }

    private fun fragmentTransaction(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment)
            .commit()
    }
}