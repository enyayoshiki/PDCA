package com.example.pdca.adapters.recyclerviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pdca.fragments.dialogs.ContentsDialogFragment
import com.example.pdca.R
import com.example.pdca.data.CycleData
import com.example.pdca.databinding.ItemAllRecyclerviewBinding

class CycleRecyclerViewAdapter(
    private val layoutInflater: LayoutInflater,
    private val cycleList: ArrayList<CycleData>,
    private val manager: FragmentManager
//        private val manager: FragmentManager
) : RecyclerView.Adapter<CycleRecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount(): Int = cycleList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemAllRecyclerviewBinding>(
            layoutInflater, R.layout.item_all_recyclerview, parent, false
        )
        return ViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cycleList[position])
        holder.contentsButton.setOnClickListener {
            ContentsDialogFragment(cycleList[position])
                .show(manager, "")
        }
    }

    class ViewHolder(private val binding: ItemAllRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val contentsButton = binding.showContentsButtonItemAllRecyclerView

        fun bind(data: CycleData?) {
            binding.cycledata = data
        }
    }
}