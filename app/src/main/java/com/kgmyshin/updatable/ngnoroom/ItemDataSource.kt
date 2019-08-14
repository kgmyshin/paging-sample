package com.kgmyshin.updatable.ngnoroom

import androidx.paging.PageKeyedDataSource
import com.kgmyshin.data.Item

internal class ItemDataSource(
  private val itemDataProvider: ItemDataProvider
) : PageKeyedDataSource<Int, Item>() {
  override fun loadInitial(
    params: LoadInitialParams<Int>,
    callback: LoadInitialCallback<Int, Item>
  ) {
    callback.onResult(
      itemDataProvider.pageList.getOrNull(0)?.itemList ?: emptyList(),
      null,
      1
    )
  }

  override fun loadAfter(
    params: LoadParams<Int>,
    callback: LoadCallback<Int, Item>
  ) {
    val page = itemDataProvider.pageList.getOrNull(params.key)
    callback.onResult(
      page?.itemList ?: emptyList(),
      if (page?.isEndPage == true) {
        null
      } else {
        params.key + 1
      }
    )
  }

  override fun loadBefore(
    params: LoadParams<Int>,
    callback: LoadCallback<Int, Item>
  ) {
    callback.onResult(
      itemDataProvider.pageList.getOrNull(params.key)?.itemList ?: emptyList(),
      params.key - 1
    )
  }
}
