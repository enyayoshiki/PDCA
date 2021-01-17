package com.example.pdca.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdca.CycleDataRepository
import com.example.pdca.data.CycleData
import kotlinx.coroutines.launch
import timber.log.Timber

class TestEditCycleViewModel : ViewModel() {

    private val cycleDataRepository = CycleDataRepository()

    var cycleData = CycleData()
    var numberCycle = 0
    var isLoading = false

    var editPlan = MutableLiveData<String>().apply {
        value = ""
    }
    var editLimit = MutableLiveData<String>().apply {
        value = ""
    }
    var editDoing = MutableLiveData<String>().apply {
        value = ""
    }
    var editCheck = MutableLiveData<String>().apply {
        value = ""
    }
    var editAction = MutableLiveData<String>().apply {
        value = ""
    }

    val isNext = MediatorLiveData<Boolean>().also { result ->
        result.addSource(editPlan) { result.value = isNext() }
        result.addSource(editLimit) { result.value = isNext() }
        result.addSource(editDoing) { result.value = isNext() }
        result.addSource(editCheck) { result.value = isNext() }
        result.addSource(editAction) { result.value = isNext() }
    }



    fun setCycleData(id: Int, number: Int) {
            Timber.i("viewModel setCycleData")
            if (!isLoading) {
                viewModelScope.launch {
                    val cycleDataList = cycleDataRepository.loadCycleData()
                    cycleDataList.filter { it.cycleid == id && it.number_of_cycle == number }
                        .forEach {
                            cycleData = it
                            numberCycle = it.number_of_cycle
                            editPlan.postValue(it.plan)
                            editLimit.postValue(it.limit)
                            editDoing.postValue(it.doing)
                            editCheck.postValue(it.check)
                            editAction.postValue(it.action)
                            Timber.i("setCycleData cycleData: $cycleData")
                        }
                    isLoading = true
                }
            }
        }

    fun updateCycleDate() {
        Timber.i("viewModel updateCycleDate")
        val editCycleData = CycleData(
            cycleid = cycleData.cycleid,
            plan = editPlan.value ?: "",
            limit = editLimit.value ?: "",
            doing = editDoing.value ?: "",
            check = editCheck.value ?: "",
            action = editAction.value ?: "",
            number_of_cycle = numberCycle
        )
//        val testCycleData = CycleData(
//            cycleid = cycleId,
//            plan =  "P",
//            limit = "L",
//            doing = "D",
//            check = "C",
//            action = "A",
//            number_of_cycle = numberCycle
//        )
        viewModelScope.launch {
            cycleDataRepository.updataCycleData(editCycleData)
        }
    }

    fun nextCycle() : CycleData{
        var baseId = 0

        if (numberCycle == 1) baseId = cycleData.cycleid else baseId = cycleData.baseId
        return  CycleData(
            cycleid = cycleData.cycleid,
            plan = editPlan.value ?: "",
            limit = editLimit.value ?: "",
            doing = editDoing.value ?: "",
            check = editCheck.value ?: "",
            action = editAction.value ?: "",
            number_of_cycle = numberCycle,
            finishcycle = 1,
            baseId = baseId
        )
        //(finishcycleを1にして完了したことにする)
//        editCycleData.finishcycle++
//        viewModelScope.launch {
//            cycleDataRepository.updataCycleData(editCycleData)
//        }
    }

    private fun isNext(): Boolean {
        return editPlan.value?.isNotEmpty() == true &&
                editLimit.value?.isNotEmpty() == true &&
                editDoing.value?.isNotEmpty() == true &&
                editCheck.value?.isNotEmpty() == true &&
                editAction.value?.isNotEmpty() == true
    }
}


