package com.userfaltakas.marvelheroesdagger_hilt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.userfaltakas.marvelheroesdagger_hilt.data.api.Result
import com.userfaltakas.marvelheroesdagger_hilt.databinding.HeroListItemBinding

class HeroesAdapter : RecyclerView.Adapter<HeroesAdapter.HeroViewHolder>() {
    inner class HeroViewHolder(val binding: HeroListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        return HeroViewHolder(
            HeroListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        val hero = differ.currentList[position]
        val binding = holder.binding

        holder.itemView.apply {
            Glide.with(this).load(hero.thumbnail?.getURL()).into(binding.image)
            binding.name.text = hero.name
            setOnClickListener {
                onItemClickListener?.let { it(hero) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (Result) -> Unit) {
        onItemClickListener = listener
    }
}