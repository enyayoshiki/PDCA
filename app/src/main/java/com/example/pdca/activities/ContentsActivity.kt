package com.example.pdca.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pdca.R
import com.example.pdca.adapters.viewpagers.ContentsActivityViewPagerAdapter
import com.example.pdca.databinding.ActivityContentsBinding
import com.google.android.material.tabs.TabLayout
import timber.log.Timber

class ContentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.i("ContentsActivity")


        val cycleId = intent.getIntExtra(EditCycleActivity.CYCLEID, 0)
        val cycleNumber = intent.getIntExtra(EditCycleActivity.CYCLENUMBER, 0)

        Timber.i("getInt: $cycleId")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contents)

        val adapter =
            ContentsActivityViewPagerAdapter(
                supportFragmentManager,
                this,
                cycleId,
                cycleNumber
            )

        binding.apply {
            viewPagerContents.adapter = adapter
            //        ここで細かいタブ設定(getTabView)
            tabLayoutContents.setupWithViewPager(binding.viewPagerContents)
            for (i in 0 until adapter.count) {
                val tab: TabLayout.Tab = binding.tabLayoutContents.getTabAt(i)!!
                tab.customView = adapter.getTabView(binding.tabLayoutContents, i)
            }
            backButtonEdit.setOnClickListener{
                MainActivity.startManiActivity(applicationContext)
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    companion object {
        const val CYCLEID = "CYCLEID"
        const val CYCLENUMBER = "CYCLENUMBER"


        fun startContents(activity: Context, cycleId: Int, cycleNumber: Int) {
            val intent = Intent(activity, ContentsActivity::class.java)
            intent.putExtra(CYCLEID, cycleId)
            intent.putExtra(CYCLENUMBER, cycleNumber)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivity(intent)
        }
    }

}