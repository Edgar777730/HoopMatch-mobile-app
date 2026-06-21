package com.example.hoopsmatch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hoopsmatch.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Проверяем, не входил ли пользователь ранее
        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val savedUsername = sharedPref.getString("USERNAME", null)

        // Если имя уже есть в памяти, сразу перекидываем на главный экран
        if (savedUsername != null) {
            DataStore.currentUser = savedUsername
            startMainActivity()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()

            if (username.isEmpty()) {
                Toast.makeText(this, "Пожалуйста, введите имя", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Сохраняем имя в память телефона
            sharedPref.edit().putString("USERNAME", username).apply()

            // Сохраняем в нашу in-memory модель
            DataStore.currentUser = username

            startMainActivity()
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Закрываем экран входа, чтобы нельзя было вернуться назад по кнопке "Back"
    }
}