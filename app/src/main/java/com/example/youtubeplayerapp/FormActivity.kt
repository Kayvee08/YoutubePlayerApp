package com.example.youtubeplayerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.youtubeplayerapp.databinding.ActivityFormBinding

class FormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}