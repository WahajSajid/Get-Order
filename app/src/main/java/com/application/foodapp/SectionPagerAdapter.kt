package com.application.foodapp

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(
    fragmentActivity: FragmentActivity,
    val sections: MutableList<Sections>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = sections.size

    override fun createFragment(position: Int): Fragment {
        return SectionFragment.newInstance(sections[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addSection(section: Sections) {
        sections.add(section)
        notifyItemInserted(sections.size - 1)
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setSections(sectionsList: List<Sections>) {
        sections.clear()
        sections.addAll(sectionsList)
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun clearSections(){
        sections.clear()
        notifyDataSetChanged()
    }
}