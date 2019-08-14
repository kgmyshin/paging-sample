package com.kgmyshin.updatable.ngnoroom

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.kgmyshin.data.Item

internal class ItemDataProvider {

  val sourceLiveData = MutableLiveData<DataSource<Int, Item>>()

  private val _pageList = mutableListOf<Page>()
  val pageList: List<Page>
    get() = _pageList

  fun addPage(page: Page) {
    _pageList.add(page)
    sourceLiveData.value?.invalidate()
  }

  fun update(item: Item) {
    val targetIndex = _pageList.indexOfFirst { page ->
      page.itemList.any { it.id == item.id }
    }

    if (targetIndex < 0) return

    _pageList[targetIndex] = _pageList[targetIndex].copy(
      itemList = _pageList[targetIndex].itemList.map {
        if (it.id == item.id) {
          item
        } else {
          it
        }
      }
    )
    sourceLiveData.value?.invalidate()
  }
}
