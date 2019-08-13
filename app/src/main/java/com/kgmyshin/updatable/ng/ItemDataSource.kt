package com.kgmyshin.updatable.ng

import androidx.paging.PageKeyedDataSource
import com.kgmyshin.data.Item
import com.kgmyshin.data.ItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ItemDataSource(
  private val itemRepository: ItemRepository
) : PageKeyedDataSource<Int, Item>() {

  private val dataSourceScope = CoroutineScope(Main + Job())

  override fun loadInitial(
    params: LoadInitialParams<Int>,
    callback: LoadInitialCallback<Int, Item>
  ) {
    dataSourceScope.launch {
      callback.onResult(
        itemRepository.findAll(page = 0),
        null,
        1
      )
    }
  }

  override fun loadAfter(
    params: LoadParams<Int>,
    callback: LoadCallback<Int, Item>
  ) {
    dataSourceScope.launch {
      callback.onResult(
        itemRepository.findAll(page = params.key),
        params.key + 1
      )
    }
  }

  override fun loadBefore(
    params: LoadParams<Int>,
    callback: LoadCallback<Int, Item>
  ) {
    // ignored, since we only ever append to our initial load
  }
}
