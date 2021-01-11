package com.example.pdca.adapters.viewpagers

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.pdca.R
import com.example.pdca.fragments.AllCycleFragment

class TabAdapter_MainActivity (fm: FragmentManager, private val context: Context) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    //タイトルの配列
    private val pageTitle = arrayOf(
            R.string.not_done_cycle,
            R.string.done_cycle
    )

    //Fragmentの配列
    override fun getItem(position: Int): Fragment {
        return AllCycleFragment.newInstance(
            position
        )
    }

    // タブの名前を出力
    override fun getPageTitle(position: Int): CharSequence? {
        return context.getString(pageTitle[position])
    }

    // タブの個数
    override fun getCount(): Int {
        return pageTitle.size
    }
}

    // タブを詳細に変更
//    fun getTabView(tabLayout: TabLayout, position: Int): ItemTabLayoutBinding? {
//        // tab_item.xml を複数
//        val binding: ItemTabLayoutBinding? = DataBindingUtil.bind(tabLayout)
//        binding?.containerItemTabLayout?.setBackgroundColor()
////        val view = LayoutInflater.from(this.context).inflate(R.layout.tablayout_homefragment, tabLayout, false)
//        // タイトル
////        val tab = view.findViewById<TextView>(R.id.tab_item_text)
////        tab.text = pageTitle[position]
////        // アイコン
////        val image = view.findViewById<ImageView>(R.id.tab_item_image)
////        image.setImageResource(pageImage[position])
//        return binding



