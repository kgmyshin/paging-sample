package com.kgmyshin.updatable.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kgmyshin.data.ItemRepository
import com.kgmyshin.updatable.room.db.InMemDatabase

internal class ItemViewModelFactory(
  private val itemRepository: ItemRepository,
  private val inMemDatabase: InMemDatabase
) : ViewModelProvider.NewInstanceFactory() {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T =
    ItemViewModel(
      itemRepository,
      inMemDatabase
    ) as T
}
