package com.daomingedu.art.mvp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class CommonViewPagerAdapter(
	fragmentManager: FragmentManager,
	val tabTitles: Array<String>,
	val fragments: MutableList<Fragment>
): FragmentPagerAdapter(fragmentManager) {
	override fun getItem(position: Int): Fragment {
		return fragments[position]
	}

	override fun getCount(): Int {
		return fragments.size
	}

	override fun getPageTitle(position: Int): CharSequence? {
		return tabTitles[position]
	}
}