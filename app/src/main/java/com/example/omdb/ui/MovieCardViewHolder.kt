package com.example.omdb.ui

import android.content.Context
import com.bumptech.glide.Glide
import com.example.domain.models.SearchBean
import com.example.omdb.base.BaseViewHolder
import com.example.omdb.databinding.ItemMovieCardBinding

class MovieCardViewHolder(
    private val binding: ItemMovieCardBinding,
    private val context: Context,
) : BaseViewHolder<SearchBean>(binding) {
    override fun setItem(data: SearchBean?, itemClickListener: (SearchBean) -> Unit) {
        data.let {
            Glide.with(context).load(it?.poster).into(binding.movieImageIV)
            binding.movieNameTV.text = it?.title
            binding.cardHolderCV.setOnClickListener { v ->
                if (it != null) {
                    itemClickListener.invoke(it)
                }
            }
        }
    }
}

