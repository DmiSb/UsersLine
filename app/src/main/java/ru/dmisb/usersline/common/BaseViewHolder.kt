package ru.dmisb.usersline.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(
    itemView: View
): RecyclerView.ViewHolder(itemView) {
    abstract fun bindView(
        data: T,
        onItemClick: ((item: T) -> Unit)?
    )
}