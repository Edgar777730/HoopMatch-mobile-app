package com.example.hoopsmatch

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hoopsmatch.databinding.ActivityCreateMatchBinding
import java.util.UUID

class CreateMatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateMatchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.btnCreate.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val court = binding.etCourt.text.toString()
            val date = binding.etDate.text.toString()
            val time = binding.etTime.text.toString()
            val maxPlayersStr = binding.etMaxPlayers.text.toString()
            val level = binding.etLevel.text.toString()
            val description = binding.etDescription.text.toString()

            if (title.isEmpty() || court.isEmpty() || date.isEmpty() || time.isEmpty() || maxPlayersStr.isEmpty()) {
                Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val maxPlayers = maxPlayersStr.toIntOrNull() ?: 10

            val newMatch = Match(
                id = UUID.randomUUID().toString(),
                title = title,
                court = court,
                date = date,
                time = time,
                players = 1,
                maxPlayers = maxPlayers,
                level = level,
                description = description,
                organizer = DataStore.currentUser // Подставляем имя вошедшего пользователя
            )

            DataStore.matches.add(0, newMatch)
            DataStore.myJoinedMatches.add(newMatch.id)

            finish()
        }
    }
}