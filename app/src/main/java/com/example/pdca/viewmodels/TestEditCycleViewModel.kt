package com.example.pdca.viewmodels

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.*
import com.example.pdca.CycleDataRepository
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

class TestEditCycleViewModel : ViewModel() {

    private val cycleDataRepository = CycleDataRepository()

    var isLoading = false
    var isNext = false
    var editPlan = MutableLiveData<String>("")
    var editLimit = MutableLiveData<String>("")
    var editDoing = MutableLiveData<String>("")
    var editCheck = MutableLiveData<String>("")
    var editAction = MutableLiveData<String>("")

    var editCycleData = CycleData(
        plan = editPlan.value ?: "",
        limit = editLimit.value ?: "",
        doing = editDoing.value ?: "",
        check = editCheck.value ?: "",
        action = editAction.value ?: ""
    )


        fun setCycleData(id: Int, number: Int) {
            Timber.i("viewModel setCycleData")
            if (!isLoading) {
                viewModelScope.launch {
                    val cycleDataList = cycleDataRepository.loadCycleData()
                    cycleDataList.filter { it.cycleid == id && it.number_of_cycle == number }
                        .forEach {
                            editPlan.postValue(it.plan)
                            editLimit.postValue(it.limit)
                            editDoing.postValue(it.doing)
                            editCheck.postValue(it.check)
                            editAction.postValue(it.action)
                        }
                    isLoading = true
                }
            }
        }

    fun updateCycleDate() {
        viewModelScope.launch {
            cycleDataRepository.updataCycleData(editCycleData)
        }
    }

    fun nextCycle() {
        //(finishcycleを1にして完了したことにする)
        editCycleData.finishcycle++
        viewModelScope.launch {
            cycleDataRepository.updataCycleData(editCycleData)
        }
    }
}


