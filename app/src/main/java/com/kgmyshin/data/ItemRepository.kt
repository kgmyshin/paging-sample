package com.kgmyshin.data

internal interface ItemRepository {
  suspend fun findAll(
    page: Int
  ): List<Item>

  suspend fun store(item: Item): Item
}
