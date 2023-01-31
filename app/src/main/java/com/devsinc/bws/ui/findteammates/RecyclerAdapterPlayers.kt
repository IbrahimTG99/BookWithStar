package com.devsinc.bws.ui.findteammates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devsinc.bws.R
import com.devsinc.bws.model.FindPlayerItem
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class RecyclerAdapterPlayers(private val playerList: List<FindPlayerItem>) :
    RecyclerView.Adapter<RecyclerAdapterPlayers.PlayerViewHolder>() {

    class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.fullName)
        private val chipGroup: ChipGroup = itemView.findViewById(R.id.chipGroup)
        private val ivProfileImage: ImageView = itemView.findViewById(R.id.iv_profile_image)


        fun bind(player: FindPlayerItem) {
            tvName.text = player.player_name
            chipGroup.removeAllViews()
            player.player_tag.forEach {
                val chip = Chip(chipGroup.context)
                chip.text = it
                chip.isCheckable = false
                chipGroup.addView(chip)
            }
            Glide.with(ivProfileImage.context).load(player.player_photo)
                .into(ivProfileImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_find_teammates_item, parent, false)
        return PlayerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(playerList[position])
    }

    override fun getItemCount() = playerList.size
}