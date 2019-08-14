package com.kgmyshin.updatable.room.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
internal interface ItemDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsert(items: ItemRecord)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun bulkUpsert(items: List<ItemRecord>)

  @Query("DELETE FROM item")
  suspend fun deleteAll()

  @Query("SELECT * FROM item")
  fun selectAll(): DataSource.Factory<Int, ItemRecord>
}
