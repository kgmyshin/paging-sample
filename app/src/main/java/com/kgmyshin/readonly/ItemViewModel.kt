package com.kgmyshin.readonly

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kgmyshin.data.ItemRepository

internal class ItemViewModel(
  itemRepository: ItemRepository
) : ViewModel() {
  val itemPagedList = LivePagedListBuilder(
    ItemDataSourceFactory(itemRepository),
    PagedList.Config.Builder()
      .setEnablePlaceholders(false)
      .setPageSize(30)
      .build()
  ).build()
}
