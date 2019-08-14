package com.kgmyshin.updatable.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
  entities = [ItemRecord::class],
  version = 1,
  exportSchema = false
)
internal abstract class InMemDatabase : RoomDatabase() {
  companion object {
    fun create(context: Context): InMemDatabase =
      Room.inMemoryDatabaseBuilder(context, InMemDatabase::class.java).fallbackToDestructiveMigration().build()
  }

  abstract fun itemDao(): ItemDao
}
