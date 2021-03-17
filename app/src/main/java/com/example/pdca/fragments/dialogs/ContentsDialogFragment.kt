package com.example.pdca.fragments.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pdca.R
import com.example.pdca.activities.ContentsActivity
import com.example.pdca.activities.EditCycleActivity
import com.example.pdca.data.CycleData
import com.example.pdca.databinding.ContentsDialogBinding
import com.example.pdca.viewmodels.CycleListViewModel
import com.example.pdca.viewmodels.ViewModelFactory_CycleList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber


class ContentsDialogFragment(private val data: CycleData) : DialogFragment() {


    private val viewModel: CycleListViewModel by viewModels {
        ViewModelFactory_CycleList(
                lifecycleScope,
                requireContext()
        )
    }

    lateinit var binding: ContentsDialogBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DataBindingUtil.inflate<ContentsDialogBinding>(
                LayoutInflater.from(requireContext()),
                R.layout.contents_dialog,
                null,
                false
        )

        viewModel.apply {
            cycleDataInViewModel = data
            loadLastCycleData(data.baseId, data.number_of_cycle-1)
            Timber.i("lastCycleData: before ${viewModel.lastCycleData}")
        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(200L)
            binding.viewmodel = viewModel
        }

        val bindingView = binding.root

        return AlertDialog.Builder(requireContext())
                .setView(bindingView) // （3） 作成したビューをコンテンツ領域に設定
                .setPositiveButton(R.string.contents_induction) { dialog, which ->
                    ContentsActivity.startContents(requireActivity(), data.cycleid, data.number_of_cycle)
                }
                .setNeutralButton(R.string.edit_button) { dialog, which ->
                    EditCycleActivity.startEditCycle(requireActivity(), data.cycleid, data.number_of_cycle)
                }
                .create()
    }
}


