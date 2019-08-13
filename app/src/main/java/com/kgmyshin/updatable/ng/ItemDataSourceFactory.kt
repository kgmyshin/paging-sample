package com.kgmyshin.updatable.ng

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.kgmyshin.data.Item
import com.kgmyshin.data.ItemRepository

internal class ItemDataSourceFactory(
  private val itemRepository: ItemRepository
) : DataSource.Factory<Int, Item>() {

  val sourceLiveData = MutableLiveData<ItemDataSource>()

  override fun create(): DataSource<Int, Item> {
    val dataSource = ItemDataSource(itemRepository)
    sourceLiveData.postValue(dataSource)
    return dataSource
  }
}
