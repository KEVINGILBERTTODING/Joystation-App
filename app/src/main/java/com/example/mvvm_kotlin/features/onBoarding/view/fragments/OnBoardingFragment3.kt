package com.example.mvvm_kotlin.features.onBoarding.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mvvm_kotlin.databinding.FragmentOnBoarding3Binding
import com.example.mvvm_kotlin.features.auth.view.activities.AuthActivity

class OnBoardingFragment3 : Fragment() {

    lateinit var binding : FragmentOnBoarding3Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoarding3Binding.inflate(inflater, container, false)

        return binding.root
    }



}