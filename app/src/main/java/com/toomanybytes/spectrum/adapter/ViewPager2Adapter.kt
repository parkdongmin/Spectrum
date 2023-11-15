package com.toomanybytes.spectrum.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.toomanybytes.spectrum.fragment.ProfilePostFragment
import com.toomanybytes.spectrum.fragment.ProfileScrapFragment
import com.toomanybytes.spectrum.fragment.ProfileSpectrumFragment

class ViewPager2Adapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProfilePostFragment()
            1 -> ProfileScrapFragment()
            2 -> ProfileSpectrumFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}