package io.github.perick.canaryweather.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment, val timestampsItem: List<Long>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return timestampsItem.size
    }

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        return WeatherFragment(timestampsItem[position])
    }
}