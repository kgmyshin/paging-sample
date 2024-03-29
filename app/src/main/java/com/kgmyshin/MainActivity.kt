package com.kgmyshin

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kgmyshin.databinding.ActivityMainBinding
import com.kgmyshin.readonly.ReadOnlyActivity
import com.kgmyshin.updatable.ng.NgActivity
import com.kgmyshin.updatable.ngnoroom.NgNoRoomActivity
import com.kgmyshin.updatable.noroom.NoRoomActivity
import com.kgmyshin.updatable.room.RoomActivity

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
      this,
      R.layout.activity_main
    )
    val adapter = ArrayAdapter<String>(
      this,
      android.R.layout.simple_list_item_1,
      Page.values().map { it.title }
    )
    binding.listView.apply {
      this.adapter = adapter
      setOnItemClickListener { _, _, position, _ ->
        Page.values()[position].startActivity(this@MainActivity)
      }
    }
  }

  enum class Page(val title: String) {
    READ_ONLY("read only") {
      override fun startActivity(context: Context) {
        context.startActivity(ReadOnlyActivity.createIntent(context))
      }
    },
    NG("ng") {
      override fun startActivity(context: Context) {
        context.startActivity(NgActivity.createIntent(context))
      }
    },
    ROOM("room") {
      override fun startActivity(context: Context) {
        context.startActivity(RoomActivity.createIntent(context))
      }
    },
    NO_ROOM("no room") {
      override fun startActivity(context: Context) {
        context.startActivity(NoRoomActivity.createIntent(context))
      }
    },
    NG_NO_ROOM("ng no room") {
      override fun startActivity(context: Context) {
        context.startActivity(NgNoRoomActivity.createIntent(context))
      }
    };

    abstract fun startActivity(context: Context)
  }
}
