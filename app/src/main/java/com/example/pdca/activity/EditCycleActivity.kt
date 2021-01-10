package com.example.pdca.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pdca.fragment.TabAdapter_EditCycleActivity
import com.example.pdca.R
import com.example.pdca.application.PdcaApplication
import com.example.pdca.databinding.ActivityEditBinding
import com.example.pdca.roomdata.CycleData
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class EditCycleActivity : AppCompatActivity() {


    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val cycleId = intent.getIntExtra(CYCLEID, 0)
        val cycleNumber = intent.getIntExtra(CYCLENUMBER, 0)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit)

        val adapter = TabAdapter_EditCycleActivity(supportFragmentManager, this, cycleId, cycleNumber)
        binding.viewPagerEdit.adapter = adapter


//        ここで細かいタブ設定(getTabView)
        binding.tabLayoutEdit.setupWithViewPager(binding.viewPagerEdit)
        for (i in 0 until adapter.count) {
            val tab: TabLayout.Tab = binding.tabLayoutEdit.getTabAt(i)!!
            tab.customView = adapter.getTabView(binding.tabLayoutEdit, i)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    companion object {
        const val CYCLEID = "CYCLEID"
        const val CYCLENUMBER = "CYCLENUMBER"

        fun startEditCycle(activity: Context, cycleId: Int, cycleNumber: Int) {
            val intent = Intent(activity, EditCycleActivity::class.java)
            intent.putExtra(CYCLEID, cycleId)
            intent.putExtra(CYCLENUMBER, cycleNumber)
            activity.startActivity(intent)
        }
    }

}