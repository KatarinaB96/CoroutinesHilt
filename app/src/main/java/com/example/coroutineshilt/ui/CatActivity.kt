package com.example.coroutineshilt.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.coroutineshilt.CatEvent
import com.example.coroutineshilt.R
import com.example.coroutineshilt.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getCat()
        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing = false
            viewModel.getCat()
        }
        observeCats()
    }

    fun observeCats() {
        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { event ->
                when (event) {
                    is CatEvent.Success -> {
                        binding.catPic.text = getString(R.string.success_message)
                        Glide.with(this@CatActivity)
                            .load(event.catImageUrl)
                            .centerCrop()
                            .into(binding.catPicture)
                    }
                    is CatEvent.Failure -> {
                        binding.catPic.text = event.errorText
                    }

                    else -> Unit
                }
            }
        }
    }
}