package com.application.foodapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.application.foodapp.databinding.FragmentGetOrderBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.newFixedThreadPoolContext

class GetOrderFragment : Fragment() {
    private lateinit var binding: FragmentGetOrderBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_get_order, container, false)

        val tableNo = sharedViewModel.tableNo.value.toString()
        binding.tableNo.text = tableNo

        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager


        //Creating object of FragmentStateAdapter to handle fragments
        val adapter = ViewPagerAdapter(requireActivity())
        viewPager.adapter = adapter
        //Attaching tab layout with view pager on run time
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Drinks Menu"
                1 -> "Food Menu"
                else -> ""
            }
        }.attach()

        return binding.root
    }

}