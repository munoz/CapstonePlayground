package com.cp_kotlin.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cp_kotlin.Fragment1
import com.cp_kotlin.Fragment2
import com.cp_kotlin.Fragment3
import com.cp_kotlin.R

private val TAB_TITLES = arrayOf(
        R.string.tab_1,
        R.string.tab_2,
        R.string.tab_3
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment = Fragment1()

        when(position) {
            0 -> fragment = Fragment1()
            1 -> fragment = Fragment2()
            2 -> fragment = Fragment3()
        }

        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 3
    }
}