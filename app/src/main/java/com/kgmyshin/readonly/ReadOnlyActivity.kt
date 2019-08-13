package com.kgmyshin.readonly

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.kgmyshin.R
import com.kgmyshin.common.ItemAdapter
import com.kgmyshin.data.impl.ItemRepositoryImpl
import com.kgmyshin.databinding.ActivityItemListBinding

class ReadOnlyActivity : AppCompatActivity() {

  companion object {
    fun createIntent(context: Context): Intent = Intent(
      context,
      ReadOnlyActivity::class.java
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
    val adapter = ItemAdapter(this)
    binding.recyclerView.adapter = adapter
    viewModel.itemPagedList.observe(
      this,
      Observer {
        adapter.submitList(it)
      }
    )
  }
}
