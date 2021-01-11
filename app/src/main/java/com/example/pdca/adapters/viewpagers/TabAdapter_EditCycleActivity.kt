package com.example.pdca.adapters.viewpagers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.pdca.R
import com.example.pdca.databinding.ItemTabLayoutBinding
import com.example.pdca.fragments.EditActionFragment
import com.example.pdca.fragments.EditCheckFragment
import com.example.pdca.fragments.EditDoFragment
import com.example.pdca.fragments.EditPlanFragment
import com.google.android.material.tabs.TabLayout


class TabAdapter_EditCycleActivity (
        fm: FragmentManager,
        private val context: Context,
        private val id: Int,
        private val number: Int)
    : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    //タイトルの配列
    private val pageTitle = arrayOf(
            R.string.icon_p,
            R.string.icon_d,
            R.string.icon_c,
            R.string.icon_a
    )

    private val pageColor = arrayOf(
            R.color.color_p,
            R.color.color_d,
            R.color.color_c,
            R.color.color_a
    )

    //Fragmentの配列
    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> EditPlanFragment.newInstance(
                id,
                number
            )
            1 -> EditDoFragment.newInstance()
            2 -> EditCheckFragment.newInstance()
            3 -> EditActionFragment.newInstance()
            else -> EditActionFragment.newInstance()
        }
    }

    // タブの名前を出力
    override fun getPageTitle(position: Int): CharSequence? {
        return context.getString(pageTitle[position])
    }

    // タブの個数
    override fun getCount(): Int {
        return pageTitle.size
    }

    fun getTabView(tabLayout: TabLayout, position: Int): View?  {

        val binding = DataBindingUtil.inflate<ItemTabLayoutBinding>(
            LayoutInflater.from(context),
            R.layout.item_tab_layout,
            tabLayout,
            false
        )

        //背景を編集
        binding?.containerItemTabLayout?.background = context.getDrawable(this.pageColor[position])
        //タイトルの編集
        binding?.titleItemTabLayout?.text =  context.getText(this.pageTitle[position])
        return binding?.root
    }
}