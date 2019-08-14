package com.kgmyshin.updatable.room.db

import com.kgmyshin.data.Item
import com.kgmyshin.data.ItemId

internal object ItemConverter {

  fun convertToRecord(itemList: List<Item>): List<ItemRecord> =
    itemList.map { convertToRecord(it) }

  fun convertToRecord(item: Item): ItemRecord = ItemRecord(
    id = item.id.value,
    name = item.name,
    checked = item.checked
  )

  fun convertToItem(recordList: List<ItemRecord>): List<Item> =
    recordList.map { convertToItem(it) }

  fun convertToItem(record: ItemRecord): Item = Item(
    id = ItemId(record.id),
    name = record.name,
    checked = record.checked
  )
}
