package com.kgmyshin.updatable.room.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
  tableName = "item"
)
data class ItemRecord(
  @PrimaryKey
  val id: Long,
  val name: String,
  val checked: Boolean
)
