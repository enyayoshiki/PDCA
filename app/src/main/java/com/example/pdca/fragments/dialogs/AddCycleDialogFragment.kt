package com.example.pdca.fragments.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pdca.R
import com.example.pdca.activities.MainActivity
import com.example.pdca.databinding.AddDialogBinding
import com.example.pdca.extention.showToast
import com.example.pdca.data.CycleData
import com.example.pdca.viewmodels.CycleListViewModel
import com.example.pdca.viewmodels.ViewModelFactory_CycleList
import timber.log.Timber

class AddCycleDialogFragment(private val cycleData: CycleData, private val isNext: Boolean) : DialogFragment(){


    private val viewModel: CycleListViewModel by viewModels {
        ViewModelFactory_CycleList(
                lifecycleScope,
                requireContext()
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding = DataBindingUtil.inflate<AddDialogBinding>(
                LayoutInflater.from(activity),
                R.layout.add_dialog,
                null,
                false
        )

        val bindingView = binding.root

        return AlertDialog.Builder(requireContext())
                .setView(bindingView) // （3） 作成したビューをコンテンツ領域に設定
                .setPositiveButton(R.string.add_new_cycle) { dialog, which ->

                    val new_plan = binding.editPlanFragmentAddDialog.text.toString()
                    val limit_plan = binding.editLimitFragmentAddDialog.text.toString()
//

                    if (new_plan.isEmpty() || limit_plan.isEmpty()) {
                        Toast.makeText(activity, R.string.alart_edit_both, Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    } else {
                        viewModel.apply {
                            insertCycleData(CycleData().apply {
                                plan = new_plan
                                limit = limit_plan
                                number_of_cycle = cycleData.number_of_cycle+1
                                if (isNext) {
                                    baseId = cycleData.baseId
                                    Timber.i("isNext false baseId: $baseId")
                                    Timber.i("isNext false numberCycle: $number_of_cycle")
                                } else {
                                    Timber.i("isNext true baseId: $baseId")
                                    Timber.i("isNext true numberCycle: $number_of_cycle")
                                }
                            })
                            if (isNext) updateCycleData(cycleData)
                        }
                        MainActivity.startManiActivity(requireContext())
                        showToast(requireContext(), R.string.excute_add_new_cycle)
                    }
                }
                .setNeutralButton(R.string.cancel_text) { dialog, which ->
                    return@setNeutralButton
                }
                .create()
    }
}