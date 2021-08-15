package com.bekzad.cryptotracker

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bekzad.cryptotracker.databinding.ActivityCoinsBinding

class CoinsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCoinsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}