package com.kgmyshin.updatable.ngnoroom

import com.kgmyshin.data.Item

internal data class Page(
  val itemList: List<Item>,
  val isEndPage: Boolean
)
