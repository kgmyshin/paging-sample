package com.kgmyshin.updatable.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kgmyshin.data.Item
import com.kgmyshin.data.ItemRepository
import com.kgmyshin.updatable.room.db.InMemDatabase
import com.kgmyshin.updatable.room.db.ItemConverter
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ItemViewModel(
  private val itemRepository: ItemRepository,
  private val inMemDatabase: InMemDatabase
) : ViewModel() {

  val itemPagedList = LivePagedListBuilder(
    inMemDatabase.itemDao().selectAll().map { ItemConverter.convertToItem(it) },
    PagedList.Config.Builder()
      .setEnablePlaceholders(false)
      .setPageSize(30)
      .build()
  ).setBoundaryCallback(
    ItemBoundaryCallback(
      itemRepository,
      inMemDatabase
    )
  ).build()

  fun check(item: Item): Job = viewModelScope.launch {
    val newItem = item.copy(checked = true)
    itemRepository.store(newItem)
    inMemDatabase.itemDao().upsert(ItemConverter.convertToRecord(newItem))
  }

  fun uncheck(item: Item): Job = viewModelScope.launch {
    val newItem = item.copy(checked = false)
    itemRepository.store(newItem)
    inMemDatabase.itemDao().upsert(ItemConverter.convertToRecord(newItem))
  }
}
