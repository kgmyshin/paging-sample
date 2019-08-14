package com.kgmyshin.updatable.ngnoroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kgmyshin.data.Item
import com.kgmyshin.data.ItemRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ItemViewModel(
  private val itemRepository: ItemRepository
) : ViewModel() {

  private val itemDataProvider = ItemDataProvider()

  val itemPagedList = LivePagedListBuilder(
    ItemDataSourceFactory(itemDataProvider),
    PagedList.Config.Builder()
      .setEnablePlaceholders(false)
      .setPageSize(30)
      .build()
  ).setBoundaryCallback(
    ItemBoundaryCallback(
      itemRepository,
      itemDataProvider
    )
  ).build()

  fun check(item: Item): Job = viewModelScope.launch {
    val newItem = item.copy(checked = true)
    itemRepository.store(newItem)
    itemDataProvider.update(newItem)
  }

  fun uncheck(item: Item): Job = viewModelScope.launch {
    val newItem = item.copy(checked = false)
    itemRepository.store(newItem)
    itemDataProvider.update(newItem)
  }
}
