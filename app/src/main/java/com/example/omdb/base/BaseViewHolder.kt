package com.example.omdb.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(viewBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    abstract fun setItem(
        data: T?,
        itemClickListener: (T) -> Unit
    )
}