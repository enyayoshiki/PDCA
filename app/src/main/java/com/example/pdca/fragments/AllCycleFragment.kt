package com.example.pdca.fragments

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pdca.adapters.recyclerviews.CycleRecyclerViewAdapter
import com.example.pdca.R
import com.example.pdca.data.CycleData
import com.example.pdca.viewmodels.CycleListViewModel
import com.example.pdca.databinding.FragmentAllCycleBinding
import com.example.pdca.viewmodels.ViewModelFactory_CycleList

class AllCycleFragment: Fragment(R.layout.fragment_all_cycle) {

    //bindingのインスタンス生成
    private lateinit var binding: FragmentAllCycleBinding

    //RecyclerView生成の下準備
    private lateinit var customAdapter: CycleRecyclerViewAdapter
    private lateinit var layoutManager: LinearLayoutManager


    //viewModelのインスタンス生成
    private val viewModel: CycleListViewModel by viewModels {
        ViewModelFactory_CycleList(
                lifecycleScope,
                requireContext()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding.unbind()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val tag = bundle?.getInt(TAG) ?: 0



        //bindingをviewと連携
        //サイクルデータの変更を受け取る
        val cycleListSnapshot = viewModel.cycleList.value ?: ArrayList<CycleData>().also {
            viewModel.cycleList.value = it
        }

        binding = DataBindingUtil.bind(view) ?: return

        //pdcaサイクルデータを取得操作
        viewModel.loadCycleData(tag)

        val manager = activity?.supportFragmentManager ?: return
        customAdapter =
            CycleRecyclerViewAdapter(
                layoutInflater,
                cycleListSnapshot,
                manager
            )

        layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.allCycleRecyclerView.also {
            it.layoutManager = layoutManager
            it.adapter = customAdapter
        }


        viewModel.cycleList.observe(viewLifecycleOwner, Observer {
            customAdapter.notifyDataSetChanged()
        })

        viewLifecycleOwner.lifecycle.addObserver(viewModel)
    }

    companion object {

        const val TAG = "TAG"
        fun newInstance(tag : Int): AllCycleFragment {
            val fragment = AllCycleFragment()
            val bundle = Bundle()
            bundle.putInt(TAG, tag)
            fragment.arguments = bundle

            return fragment
        }
    }
}