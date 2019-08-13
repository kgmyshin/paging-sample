package com.kgmyshin.readonly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kgmyshin.data.ItemRepository

internal class ItemViewModelFactory(
  private val itemRepository: ItemRepository
) : ViewModelProvider.NewInstanceFactory() {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T = ItemViewModel(itemRepository) as T
}
