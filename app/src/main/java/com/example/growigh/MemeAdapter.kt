package com.example.growigh

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptotracker.model

class MemeAdapter(private val context: Context, private val memeList: List<model>) :
    RecyclerView.Adapter<MemeAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.meme_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val memeItem = memeList[position]
        Glide.with(context)
            .load(
                memeItem.img)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return memeList.size
    }
}