package com.puneet.codeexamples.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.puneet.codeexamples.databinding.ItemViewPagerBinding

class ViewPagerAdapter(private val list: List<String>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val url = list[position]
        val binding =
            ItemViewPagerBinding.inflate(LayoutInflater.from(container.context), container, false)
        Glide.with(container.context)
            .load(url)
            .into(binding.root)
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }
}