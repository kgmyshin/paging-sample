package com.kgmyshin.updatable.ng

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.kgmyshin.R
import com.kgmyshin.common.ItemAdapter
import com.kgmyshin.common.OnItemClickListener
import com.kgmyshin.data.Item
import com.kgmyshin.data.impl.ItemRepositoryImpl
import com.kgmyshin.databinding.ActivityItemListBinding

class NgActivity : AppCompatActivity() {

  companion object {
    fun createIntent(context: Context): Intent = Intent(
      context,
      NgActivity::class.java
    )
  }

  private val viewModel: ItemViewModel by viewModels {
    ItemViewModelFactory(ItemRepositoryImpl())
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = DataBindingUtil.setContentView<ActivityItemListBinding>(
      this,
      R.layout.activity_item_list
    )
    val adapter = ItemAdapter(this).apply {
      onItemClickListener = object : OnItemClickListener {
        override fun onClick(item: Item) {
          if (item.checked) {
            viewModel.uncheck(item)
          } else {
            viewModel.check(item)
          }
        }
      }
    }
    binding.recyclerView.adapter = adapter
    viewModel.itemPagedList.observe(
      this,
      Observer {
        adapter.submitList(it)
      }
    )
  }
}
