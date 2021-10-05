package com.puneet.codeexamples.viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.MenuItem
import androidx.core.view.get
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.viewpager.widget.ViewPager
import com.puneet.codeexamples.R
import com.puneet.codeexamples.databinding.ActivityViewPagerBinding
import kotlin.math.roundToInt

class ViewPagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewPagerBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var indicatorAdapter: ViewPagerIndicatorAdapter

    private var lastSelectedPosition = 0

    companion object {
        private const val TAG = "ViewPagerActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewPager(
            listOf(
                "https://source.unsplash.com/-1h_NN3nqzI",
                "https://source.unsplash.com/j_AZBw3FqZI",
                "https://source.unsplash.com/TkAPnJaOPZo",
                "https://source.unsplash.com/ssgXahymoRE",
                "https://source.unsplash.com/FbN2z3bEaSs",
                "https://source.unsplash.com/DnxzJlBoKlo"
            )
        )
    }

    private fun setupViewPager(predefinedList: List<String>) {
        with(binding.viewPager) {
            viewPagerAdapter = ViewPagerAdapter(predefinedList)
            adapter = viewPagerAdapter
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    // NO-OP
                }

                override fun onPageSelected(position: Int) {
                    if (lastSelectedPosition != position) {
                        lastSelectedPosition = position
                        indicatorAdapter.setSelectedItem(lastSelectedPosition)
                        binding.pagerIndicator.scrollToPosition(lastSelectedPosition)
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {
                    // NO-OP
                }
            })
        }
        with(binding.pagerIndicator) {
            indicatorAdapter = ViewPagerIndicatorAdapter()
            adapter = indicatorAdapter
            indicatorAdapter.setOnClickListener(object : ViewPagerIndicatorAdapter.OnClickListener {
                override fun onClick(position: Int) {
                    binding.viewPager.setCurrentItem(position, true)
                }
            })

            indicatorAdapter.submitData(predefinedList)
            addItemDecoration(
                SpaceItemDecoration(
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        16.0f,
                        resources.displayMetrics
                    ).roundToInt(),
                    addSpaceAboveFirstItem = true,
                    addSpaceBelowLastItem = true
                )
            )
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}