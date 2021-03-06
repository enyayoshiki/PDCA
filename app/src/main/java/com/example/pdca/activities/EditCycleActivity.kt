package com.example.pdca.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pdca.adapters.viewpagers.EditCycleActivityViewPagerAdapter
import com.example.pdca.R
import com.example.pdca.databinding.ActivityEditBinding
import com.google.android.material.tabs.TabLayout

class EditCycleActivity : AppCompatActivity() {


    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val cycleId = intent.getIntExtra(CYCLEID, 0)
        val cycleNumber = intent.getIntExtra(CYCLENUMBER, 0)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit)


        //ViewPagerAdapterの設定
        val adapter = setViewPagerAdapter(cycleId, cycleNumber)

//        val adapter =
//            EditCycleActivityViewPagerAdapter(
//                supportFragmentManager,
//                this,
//                cycleId,
//                cycleNumber
//            )

        binding.apply {
            viewPagerEdit.adapter = adapter
            //        ここで細かいタブ設定(getTabView)
            setTabLayout(adapter)

            backButtonEdit.setOnClickListener{
                MainActivity.startManiActivity(applicationContext)
            }
            goContentsButtonEdit.setOnClickListener{
                ContentsActivity.startContents(applicationContext, cycleId, cycleNumber)
            }
        }
    }

    //ViewPagerAdapterの設定
    private fun setViewPagerAdapter(cycleId: Int, cycleNumber: Int): EditCycleActivityViewPagerAdapter{
         return EditCycleActivityViewPagerAdapter(
            supportFragmentManager,
            this,
            cycleId,
            cycleNumber
        )
    }
    //TabLayoutの設定
    private fun setTabLayout(adapter: EditCycleActivityViewPagerAdapter){
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
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivity(intent)
        }
    }

}