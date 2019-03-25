package com.defate.mac.androidcommondemo.samples.adapter

import android.support.v7.util.DiffUtil
import com.defate.mac.androidcommondemo.samples.room.Scp

class ScpDiffCallback : DiffUtil.ItemCallback<Scp>() {

    override fun areItemsTheSame(oldItem: Scp, newItem: Scp): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Scp, newItem: Scp): Boolean {
        return oldItem == newItem
    }
}