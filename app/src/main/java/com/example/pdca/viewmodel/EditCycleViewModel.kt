package com.example.pdca.viewmodel

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.text.BoringLayout
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import com.example.pdca.R
import com.example.pdca.activity.MainActivity
import com.example.pdca.application.PdcaApplication
import com.example.pdca.extention.showToast
import com.example.pdca.fragment.AddCycleDialogFragment
import com.example.pdca.roomdata.CycleData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class EditCycleViewModel(
        private var coroutineScope: CoroutineScope,
        application: Application,
        private var context: Context
) : AndroidViewModel(application), LifecycleObserver {

    private val cycleDao = PdcaApplication.db.pdcaCycleDao()

    private var cycleData = CycleData()
    private lateinit var fragmentManager: FragmentManager

    var editPlan = ObservableField("")
    var editLimit = ObservableField("")
    var editDoing = ObservableField("")
    var editCheck = ObservableField("")
    var editAction = ObservableField("")

    var isLoading = ObservableBoolean(false)

    fun setCycleData(id: Int, number: Int) {
        if (!isLoading.get()) {
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    Timber.i("setCycleData")
                    val allData = cycleDao.getAllData()
                    allData.filter { it.cycleid == id && it.number_of_cycle == number }
                            .forEach {
                                cycleData = it
                                editPlan.set(it.plan)
                                editLimit.set(it.limit)
                                editDoing.set(it.doing)
                                editCheck.set(it.check)
                                editAction.set(it.action)
                            }
                }
            }
            isLoading.set(true)
        }
    }

    fun updateCycleDate(isNext: Boolean) {

        val editCycleData = cycleData.apply {
            plan = editPlan.get().toString()
            limit = editLimit.get().toString()
            doing = editDoing.get().toString()
            check = editCheck.get().toString()
            action = editAction.get().toString()
        }

        Timber.i("updateCycleDate: editCycleData: $editCycleData")

        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                cycleDao.update(editCycleData)

                if (isNext){
                    AddCycleDialogFragment(editCycleData).show(fragmentManager, "")
                }
                else MainActivity.startManiActivity(context)
            }
            showToast(context, R.string.save_text)
        }
        Timber.i("updateCycleDate: out Io")
    }

    fun makeNextCycle(manager: FragmentManager) {

        AlertDialog.Builder(context) // FragmentではActivityを取得して生成
                .setMessage(R.string.save_and_next)
                .setPositiveButton(R.string.ok) { dialog, which ->
                    if (
                            editPlan.get().toString().isNotEmpty() &&
                            editLimit.get().toString().isNotEmpty() &&
                            editDoing.get().toString().isNotEmpty() &&
                            editCheck.get().toString().isNotEmpty() &&
                            editAction.get().toString().isNotEmpty()) {

                        Timber.i("updateCycleDate: makeNextCycle: true")

                        fragmentManager = manager
                        updateCycleDate(true)

                    }
                    else showToast(context, R.string.please_edit_allcontents)
                }
                .setNegativeButton(R.string.no) { dialog, which ->
                    Timber.i("updateCycleDate: makeNextCycle: false")
                    return@setNegativeButton
                }
                .show()
    }

}
