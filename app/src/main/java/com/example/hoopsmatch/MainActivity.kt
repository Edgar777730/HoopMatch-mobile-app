package com.example.hoopsmatch

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hoopsmatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MatchAdapter
    private var showOnlyMyMatches = false // Состояние переключателя

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        // Обновляем список, если вернулись с другого экрана
        filterList()
    }

    private fun setupRecyclerView() {
        adapter = MatchAdapter(DataStore.matches) { matchId ->
            // Переход на экран подробностей по клику
            val intent = Intent(this, MatchDetailActivity::class.java)
            intent.putExtra("MATCH_ID", matchId)
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setupListeners() {
        // Кнопка создания матча
        binding.fabCreateMatch.setOnClickListener {
            startActivity(Intent(this, CreateMatchActivity::class.java))
        }

        // Слушатель для переключателя Все/Мои матчи
        binding.toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                showOnlyMyMatches = (checkedId == R.id.btnMyMatches)
                filterList()
            }
        }

        // Слушатель для строки поиска
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterList()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterList() {
        val query = binding.etSearch.text.toString()

        // Сначала берем весь список матчей
        var filteredList = DataStore.matches.toList()

        // Если выбрана вкладка "Мои матчи", оставляем только те, чей ID есть в myJoinedMatches
        if (showOnlyMyMatches) {
            filteredList = filteredList.filter { DataStore.myJoinedMatches.contains(it.id) }
        }

        // Затем фильтруем по строке поиска, если она не пустая
        if (query.isNotEmpty()) {
            filteredList = filteredList.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.court.contains(query, ignoreCase = true)
            }
        }

        adapter.updateData(filteredList)
    }
}