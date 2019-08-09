package com.kgmyshin.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kgmyshin.data.Item
import com.kgmyshin.databinding.ViewItemBinding

internal class ItemViewHolder(
  private val binding: ViewItemBinding
) : RecyclerView.ViewHolder(binding.root) {
  companion object {
    fun create(
      inflater: LayoutInflater,
      container: ViewGroup,
      attachToRoot: Boolean
    ): ItemViewHolder = ItemViewHolder(
      ViewItemBinding.inflate(
        inflater,
        container,
        attachToRoot
      )
    )
  }

  fun bind(item: Item?) {
    binding.item = item
    binding.executePendingBindings()
  }
}
