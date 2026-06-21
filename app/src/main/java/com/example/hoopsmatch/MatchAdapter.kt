package com.example.hoopsmatch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hoopmatch.databinding.ItemMatchBinding

class MatchAdapter(
    private var matchList: List<Match>,
    private val onDetailsClick: (String) -> Unit
) : RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    inner class MatchViewHolder(private val binding: ItemMatchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(match: Match) {
            binding.tvTitle.text = match.title
            binding.tvCourt.text = match.court
            binding.tvDateTime.text = "${match.date} • ${match.time}"
            binding.tvPlayers.text = "Игроки: ${match.players}/${match.maxPlayers}"
            binding.tvLevel.text = "Уровень: ${match.level}"

            binding.btnDetails.setOnClickListener {
                onDetailsClick(match.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val binding = ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(matchList[position])
    }

    override fun getItemCount(): Int = matchList.size

    fun updateData(newList: List<Match>) {
        matchList = newList
        notifyDataSetChanged() // Обновляем список на экране
    }
}