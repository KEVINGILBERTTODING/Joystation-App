package com.example.mvvm_kotlin.features.onBoarding.view.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mvvm_kotlin.features.onBoarding.view.activities.OnBoardingActivity
import com.example.mvvm_kotlin.features.onBoarding.view.fragments.OnBoardingFragment1
import com.example.mvvm_kotlin.features.onBoarding.view.fragments.OnBoardingFragment2
import com.example.mvvm_kotlin.features.onBoarding.view.fragments.OnBoardingFragment3
import com.example.mvvm_kotlin.features.onBoarding.view.fragments.OnBoardingFragment4

class OnboardingAdapter (onBoardingActivity: OnBoardingActivity) :FragmentStateAdapter(onBoardingActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return OnBoardingFragment1()
        }else if (position == 1) {
            return OnBoardingFragment2()
        }else if (position == 2) {
            return OnBoardingFragment3()
        }else if (position == 3) {
            return OnBoardingFragment4()
        }

        else {
            throw IllegalAccessException("Invalid position: $position")
        }
    }

}