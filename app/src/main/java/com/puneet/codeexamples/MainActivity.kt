package com.puneet.codeexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.ui.unit.ExperimentalUnitApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.puneet.codeexamples.compose.ComposeActivity
import com.puneet.codeexamples.databinding.ActivityMainBinding
import com.puneet.codeexamples.viewpager.ViewPagerActivity
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalPagerApi
@ExperimentalUnitApi
@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.viewPagerCta.setOnClickListener {
            startActivity(Intent(this, ViewPagerActivity::class.java))
        }
        binding.composeCta.setOnClickListener {
            startActivity(Intent(this, ComposeActivity::class.java))
        }
    }
}