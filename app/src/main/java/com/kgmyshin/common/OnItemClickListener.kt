package com.kgmyshin.common

import com.kgmyshin.data.Item

internal interface OnItemClickListener {
  fun onClick(item: Item)
}
