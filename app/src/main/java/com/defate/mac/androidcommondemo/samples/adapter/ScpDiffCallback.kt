package com.defate.mac.androidcommondemo.samples.adapter

import androidx.recyclerview.widget.DiffUtil
import com.defate.mac.androidcommondemo.samples.room.Scp

class ScpDiffCallback : DiffUtil.ItemCallback<Scp>() {

    override fun areItemsTheSame(oldItem: Scp, newItem: Scp): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Scp, newItem: Scp): Boolean {
        return oldItem.scpContent == newItem.scpContent
    }
}