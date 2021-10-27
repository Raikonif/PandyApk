package com.dai.pandyapk.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dai.pandyapk.databinding.ActivityReaderBinding

class ReaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityReaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}