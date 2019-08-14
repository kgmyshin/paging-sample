package com.kgmyshin.updatable.ngnoroom

import androidx.paging.DataSource
import com.kgmyshin.data.Item

internal class ItemDataSourceFactory(
  private val itemDataProvider: ItemDataProvider
) : DataSource.Factory<Int, Item>() {
  override fun create(): DataSource<Int, Item> {
    val source = ItemDataSource(itemDataProvider)
    itemDataProvider.sourceLiveData.postValue(source)
    return source
  }
}
