package com.kgmyshin.updatable.noroom

import androidx.paging.ItemKeyedDataSource
import com.kgmyshin.data.Item
import com.kgmyshin.data.ItemId

internal class ItemDataSource(
  private val itemDataProvider: ItemDataProvider
) : ItemKeyedDataSource<ItemId, Item>() {

  override fun loadInitial(
    params: LoadInitialParams<ItemId>,
    callback: LoadInitialCallback<Item>
  ) {
    val requestedInitialKey = params.requestedInitialKey
    if (requestedInitialKey == null && itemDataProvider.pageList.isEmpty()) {
      callback.onResult(emptyList())
    } else if (requestedInitialKey == null) {
      callback.onResult(itemDataProvider.pageList[0].itemList)
    } else {
      itemDataProvider.pageList.forEach { page ->
        if (page.itemList.any { it.id == requestedInitialKey }) {
          callback.onResult(page.itemList)
          return
        }
      }
    }
  }

  override fun loadAfter(
    params: LoadParams<ItemId>,
    callback: LoadCallback<Item>
  ) {
    itemDataProvider.pageList.forEachIndexed { index, page ->
      if (page.itemList.lastOrNull()?.id == params.key && !page.isEndPage) {
        val nextItemList = itemDataProvider.pageList.getOrNull(index + 1)?.itemList ?: emptyList()
        callback.onResult(nextItemList)
        return
      }
    }
    callback.onResult(emptyList())
  }

  override fun loadBefore(
    params: LoadParams<ItemId>,
    callback: LoadCallback<Item>
  ) {
    itemDataProvider.pageList.forEachIndexed { index, page ->
      if (page.itemList.firstOrNull()?.id == params.key) {
        val prevItemList = itemDataProvider.pageList.getOrNull(index - 1)?.itemList ?: emptyList()
        callback.onResult(prevItemList)
        return
      }
    }
    callback.onResult(emptyList())
  }

  override fun getKey(item: Item): ItemId = item.id
}
