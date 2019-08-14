package com.kgmyshin.updatable.room

import androidx.paging.PagedList
import com.kgmyshin.data.Item
import com.kgmyshin.data.ItemRepository
import com.kgmyshin.updatable.room.db.InMemDatabase
import com.kgmyshin.updatable.room.db.ItemConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ItemBoundaryCallback(
  private val itemRepository: ItemRepository,
  private val inMemDatabase: InMemDatabase
) : PagedList.BoundaryCallback<Item>() {

  private val boundaryCallbackScope = CoroutineScope(Main + Job())
  private val pageList = mutableListOf<Page>()

  override fun onZeroItemsLoaded() {
    super.onZeroItemsLoaded()
    if (pageList.isNotEmpty() && pageList[0].isEndPage) return
    boundaryCallbackScope.launch {
      val itemList = itemRepository.findAll(page = 0)
      if (itemList.isNotEmpty()) {
        bulkInsertToDatabase(itemList)
      }
      pageList.clear()
      pageList.add(
        Page(
          lastItem = itemList.lastOrNull(),
          isEndPage = itemList.isEmpty()
        )
      )
    }
  }

  override fun onItemAtEndLoaded(itemAtEnd: Item) {
    super.onItemAtEndLoaded(itemAtEnd)

    val currentPage = pageList.firstOrNull { it.lastItem == itemAtEnd }
    if (currentPage == null || currentPage.isEndPage) return
    val currentPageIndex = pageList.indexOf(currentPage)

    boundaryCallbackScope.launch {
      val nextPageIndex = currentPageIndex + 1
      val itemList = itemRepository.findAll(page = nextPageIndex)
      if (itemList.isNotEmpty()) {
        bulkInsertToDatabase(itemList)
      }
      pageList.add(
        Page(
          lastItem = itemList.lastOrNull(),
          isEndPage = itemList.isEmpty()
        )
      )
    }
  }

  private suspend fun bulkInsertToDatabase(itemList: List<Item>) {
    val dao = inMemDatabase.itemDao()
    dao.bulkUpsert(ItemConverter.convertToRecord(itemList))
  }

  data class Page(
    val lastItem: Item?,
    val isEndPage: Boolean = true
  )
}
