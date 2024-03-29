package com.kgmyshin.updatable.noroom

import androidx.paging.DataSource
import com.kgmyshin.data.Item
import com.kgmyshin.data.ItemId

internal class ItemDataSourceFactory(
  private val itemDataProvider: ItemDataProvider
) : DataSource.Factory<ItemId, Item>() {
  override fun create(): DataSource<ItemId, Item> {
    val source = ItemDataSource(itemDataProvider)
    itemDataProvider.sourceLiveData.postValue(source)
    return source
  }
}
