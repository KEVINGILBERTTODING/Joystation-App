package com.example.mvvm_kotlin.features.onBoarding.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mvvm_kotlin.R
import com.example.mvvm_kotlin.databinding.ActivityOnBoardingBinding
import com.example.mvvm_kotlin.features.onBoarding.view.adapters.OnboardingAdapter

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val onboardingAdapter = OnboardingAdapter(this)
        binding.viewPager.adapter = onboardingAdapter

        binding.dotsIndicator.attachTo(binding.viewPager)


    }


}