package io.github.perick.canaryweather.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.github.perick.canaryweather.view.WeatherFragment

class ViewPagerAdapter(fragment: Fragment, val timestampsItem: List<Long>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return timestampsItem.size
    }

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        return WeatherFragment(timestampsItem[position])
    }
}