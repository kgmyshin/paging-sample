package com.kgmyshin.data.impl

import com.kgmyshin.data.Item
import com.kgmyshin.data.ItemId
import com.kgmyshin.data.ItemRepository

internal class ItemRepositoryImpl : ItemRepository {

  private val pageCache = mutableMapOf<Int, List<Item>>()

  override suspend fun findAll(
    page: Int
  ): List<Item> = if (pageCache[page] != null) {
    pageCache[page] ?: emptyList()
  } else {
    val newItemList = (1..30).map {
      Item(
        id = ItemId(30 * page + it.toLong()),
        name = "アイテム - ${30 * page + it}",
        checked = false
      )
    }
    pageCache[page] = newItemList
    newItemList
  }

  override suspend fun store(item: Item): Item {
    pageCache.keys.forEach { key ->
      pageCache[key] = pageCache[key]?.map {
        if (it.id == item.id) {
          item
        } else {
          it
        }
      } ?: emptyList()
    }
    return item
  }
}
