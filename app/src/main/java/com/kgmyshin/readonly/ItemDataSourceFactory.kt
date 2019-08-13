package com.kgmyshin.readonly

import androidx.paging.DataSource
import com.kgmyshin.data.Item
import com.kgmyshin.data.ItemRepository

internal class ItemDataSourceFactory(
  private val itemRepository: ItemRepository
) : DataSource.Factory<Int, Item>() {
  override fun create(): DataSource<Int, Item> = ItemDataSource(itemRepository)
}
