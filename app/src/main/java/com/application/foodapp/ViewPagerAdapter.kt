package com.application.foodapp

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }


    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> DrinkMenuFragment()
            1 ->FoodMenuFragment()
            else ->Fragment()
        }
    }


}