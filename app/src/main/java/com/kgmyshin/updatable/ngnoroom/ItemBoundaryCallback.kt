package com.kgmyshin.updatable.ngnoroom

import androidx.paging.PagedList
import com.kgmyshin.data.Item
import com.kgmyshin.data.ItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ItemBoundaryCallback(
  private val itemRepository: ItemRepository,
  private val itemDataProvider: ItemDataProvider
) : PagedList.BoundaryCallback<Item>() {

  private val boundaryCallbackScope = CoroutineScope(Dispatchers.Main + Job())

  override fun onZeroItemsLoaded() {
    super.onZeroItemsLoaded()

    if (itemDataProvider.pageList.isNotEmpty() && itemDataProvider.pageList[0].isEndPage) return
    boundaryCallbackScope.launch {
      val itemList = itemRepository.findAll(page = 0)
      itemDataProvider.addPage(
        page = Page(
          itemList = itemList,
          isEndPage = itemList.isEmpty()
        )
      )
    }
  }

  override fun onItemAtEndLoaded(itemAtEnd: Item) {
    super.onItemAtEndLoaded(itemAtEnd)

    val currentPage = itemDataProvider.pageList.firstOrNull { it.itemList.lastOrNull() == itemAtEnd }
    if (currentPage == null || currentPage.isEndPage) return
    val currentPageIndex = itemDataProvider.pageList.indexOf(currentPage)

    boundaryCallbackScope.launch {
      val nextPageIndex = currentPageIndex + 1
      val itemList = itemRepository.findAll(page = nextPageIndex)
      itemDataProvider.addPage(
        Page(
          itemList = itemList,
          isEndPage = itemList.isEmpty()
        )
      )
    }
  }

  override fun onItemAtFrontLoaded(itemAtFront: Item) {
    super.onItemAtFrontLoaded(itemAtFront)
  }
}
