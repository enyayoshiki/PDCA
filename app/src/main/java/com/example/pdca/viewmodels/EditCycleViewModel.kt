package com.example.pdca.viewmodels

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pdca.R
import com.example.pdca.activities.MainActivity
import com.example.pdca.application.PdcaApplication
import com.example.pdca.extention.showToast
import com.example.pdca.fragments.AddCycleDialogFragment
import com.example.pdca.data.CycleData
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

    var editPlan = MutableLiveData("")
    var editLimit = MutableLiveData("")
    var editDoing = MutableLiveData("")
    var editCheck = MutableLiveData("")
    var editAction = MutableLiveData("")

    var isLoading = ObservableBoolean(false)

    fun setCycleData(id: Int, number: Int) {
        if (!isLoading.get()) {
            CoroutineScope(Dispatchers.Main).launch {

                withContext(Dispatchers.IO) {

                    Timber.i("setCycleData")
                    val allData = cycleDao.getAllData()
                    allData.filter { it.cycleid == id && it.number_of_cycle == number }
                            .forEach {
                                cycleData = it
                                editPlan.postValue(it.plan)
                                editLimit.postValue(it.limit)
                                editDoing.postValue(it.doing)
                                editCheck.postValue(it.check)
                                editAction.postValue(it.action)
                            }
                }
            }
            isLoading.set(true)
        }
    }

    fun updateCycleDate(isNext: Boolean) {

        val editCycleData = cycleData.apply {
            plan = editPlan.value.toString()
            limit = editLimit.value.toString()
            doing = editDoing.value.toString()
            check = editCheck.value.toString()
            action = editAction.value.toString()
        }

        Timber.i("updateCycleDate: editCycleData: $editCycleData")

        CoroutineScope(Dispatchers.IO).launch {

            withContext(Dispatchers.Main) {
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
                            editPlan.value.toString().isNotEmpty() &&
                            editLimit.value.toString().isNotEmpty() &&
                            editDoing.value.toString().isNotEmpty() &&
                            editCheck.value.toString().isNotEmpty() &&
                            editAction.value.toString().isNotEmpty()) {

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
