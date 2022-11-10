package com.example.omdb.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.models.SearchBean
import com.example.omdb.base.BaseViewHolder
import com.example.omdb.databinding.ItemMovieCardBinding

class MoviesAdapter(
    private val itemClickListener: (SearchBean) -> Unit,
    private val context: Context
) : ListAdapter<SearchBean, BaseViewHolder<*>>(DIFF_CALLBACK) {


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchBean>() {
            override fun areItemsTheSame(
                oldItem: SearchBean,
                newItem: SearchBean
            ): Boolean {
                return oldItem.imdbID == newItem.imdbID
            }

            override fun areContentsTheSame(
                oldItem: SearchBean,
                newItem: SearchBean
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MovieCardViewHolder(
            ItemMovieCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), context
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        (holder as MovieCardViewHolder).setItem(
            currentList[position], itemClickListener
        )
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)

    }


}