package com.example.hoopsmatch

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hoopsmatch.databinding.ActivityMatchDetailBinding
import com.google.android.material.snackbar.Snackbar

class MatchDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchDetailBinding
    private var currentMatch: Match? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        binding.toolbar.setNavigationOnClickListener { finish() }

        val matchId = intent.getStringExtra("MATCH_ID")
        currentMatch = matchId?.let { DataStore.getMatchById(it) }

        if (currentMatch == null) {
            finish()
            return
        }

        bindData()
        setupListeners()
    }

    private fun bindData() {
        currentMatch?.let { match ->
            binding.tvTitle.text = match.title
            binding.tvCourt.text = "Адрес: ${match.court}"
            binding.tvDateTime.text = "${match.date} • ${match.time}"
            binding.tvPlayers.text = "Собрано игроков: ${match.players}/${match.maxPlayers}"
            binding.tvLevel.text = "Уровень: ${match.level}"
            binding.tvDescription.text = match.description
            binding.tvOrganizer.text = match.organizer

            if (DataStore.myJoinedMatches.contains(match.id)) {
                binding.btnJoin.isEnabled = false
                binding.btnJoin.text = "Вы уже участвуете"
            } else if (match.players >= match.maxPlayers) {
                binding.btnJoin.isEnabled = false
                binding.btnJoin.text = getString(R.string.match_full)
            }
        }
    }

    private fun setupListeners() {
        binding.btnJoin.setOnClickListener {
            currentMatch?.let { match ->
                if (match.players < match.maxPlayers && !DataStore.myJoinedMatches.contains(match.id)) {
                    match.players += 1
                    DataStore.myJoinedMatches.add(match.id)
                    bindData()
                    Snackbar.make(binding.root, R.string.joined_success, Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        binding.layoutOrganizer.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("ORGANIZER_NAME", currentMatch?.organizer)
            startActivity(intent)
        }
    }
}