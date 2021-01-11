package com.example.pdca.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pdca.activities.ContensActivity
import com.example.pdca.activities.EditCycleActivity
import com.example.pdca.R
import com.example.pdca.data.CycleData
import com.example.pdca.viewmodels.CycleListViewModel
import com.example.pdca.databinding.ContentsDialogBinding
import com.example.pdca.viewmodels.ViewModelFactory_CycleList


class ContentsDialogFragment(private val cycleData: CycleData) : DialogFragment() {


    private val viewModel: CycleListViewModel by viewModels {
        ViewModelFactory_CycleList(
                lifecycleScope,
                requireContext()
        )
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding = DataBindingUtil.inflate<ContentsDialogBinding>(
                LayoutInflater.from(activity),
                R.layout.contents_dialog,
                null,
                false
        )

        val lastCycleData = viewModel.lastCycleList.value?.get(0)
        var cycleNumber = cycleData.number_of_cycle

        viewModel.loadLastCycleData(cycleData.cycleid, cycleNumber--)
        binding.cycledata = cycleData
        binding.lastcycledata = lastCycleData

        val bindingView = binding.root

        return AlertDialog.Builder(requireContext())
                .setView(bindingView) // （3） 作成したビューをコンテンツ領域に設定
                .setPositiveButton(R.string.contents_induction) { dialog, which ->
                    ContensActivity.startContents(requireActivity(), cycleData.cycleid, cycleData.number_of_cycle)

                }
                .setNeutralButton(R.string.edit_button) { dialog, which ->
                    EditCycleActivity.startEditCycle(requireActivity(), cycleData.cycleid, cycleData.number_of_cycle)
                }
                .create()
    }
}


