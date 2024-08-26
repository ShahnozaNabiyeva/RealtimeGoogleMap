package com.shahnoza.realtimegooglemap

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity


import com.shahnoza.realtimegooglemap.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            val intent= Intent(this,Map::class.java)
            startActivity(intent)
        }

    }
}
