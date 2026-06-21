package com.example.hoopsmatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hoopsmatch.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        binding.toolbar.setNavigationOnClickListener { finish() }

        val organizerName = intent.getStringExtra("ORGANIZER_NAME") ?: "Неизвестный организатор"

        binding.tvName.text = organizerName
        binding.tvAgePosition.text = "24 года, Разыгрывающий защитник (PG)"
        binding.tvMatchCount.text = (5..30).random().toString()
        binding.tvRating.text = "4.${(1..9).random()}"
    }
}