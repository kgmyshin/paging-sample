package com.kgmyshin.updatable.ng

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

  private val dataSourceFactory = ItemDataSourceFactory(itemRepository)

  val itemPagedList = LivePagedListBuilder(
    dataSourceFactory,
    PagedList.Config.Builder()
      .setEnablePlaceholders(false)
      .setPageSize(30)
      .build()
  ).build()

  fun check(item: Item): Job = viewModelScope.launch {
    itemRepository.store(item.copy(checked = true))
    dataSourceFactory.sourceLiveData.value?.invalidate()
  }

  fun uncheck(item: Item): Job = viewModelScope.launch {
    itemRepository.store(item.copy(checked = false))
    dataSourceFactory.sourceLiveData.value?.invalidate()
  }
}
