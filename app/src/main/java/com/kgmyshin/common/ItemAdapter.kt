package com.kgmyshin.common

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.kgmyshin.data.Item

internal class ItemAdapter(context: Context) : PagedListAdapter<Item, ItemViewHolder>(ITEM_CALLBACK) {

  companion object {
    private val ITEM_CALLBACK = object : DiffUtil.ItemCallback<Item>() {
      override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem.id == newItem.id

      override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem == newItem
    }
  }

  var onItemClickListener: OnItemClickListener? = null
  private val inflater = LayoutInflater.from(context)

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ItemViewHolder = ItemViewHolder.create(
    inflater,
    parent,
    false
  )

  override fun onBindViewHolder(
    holder: ItemViewHolder,
    position: Int
  ) = holder.bind(
    getItem(position),
    onItemClickListener
  )
}
