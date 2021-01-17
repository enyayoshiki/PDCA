package com.example.pdca.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.pdca.application.PdcaApplication
import com.example.pdca.data.CycleData
import kotlinx.coroutines.*
import timber.log.Timber

class CycleListViewModel(
    var coroutineScope: CoroutineScope,
    application: Application
) : AndroidViewModel(application), LifecycleObserver {

    private val cycleDao = PdcaApplication.db.pdcaCycleDao()

    val cycleList = MutableLiveData<ArrayList<CycleData>>()
    var cycleDataInViewModel = CycleData()
    var lastCycleData = CycleData()

    fun loadCycleData(tag: Int) {
        coroutineScope.launch {
            val cycleListSnapshot = cycleList.value ?: ArrayList()
            cycleListSnapshot.clear()

            val cycleListResponse = fetchPublicTimeline()

            //0は未解決、1は解決済み
            if (tag == 0) {
                cycleListResponse.forEach {
                    //"finishcycle"が"0"なら未解決のもの
                    if (it.finishcycle == 0) {
                        cycleListSnapshot.add(it)
                    }
                }
                cycleList.postValue(cycleListSnapshot)
            } else {
                cycleListResponse.forEach {
                    //"finishcycle"が"0"なら未解決のもの
                    if (it.finishcycle == 1) {
                        cycleListSnapshot.add(it)
                    }
                }
                cycleList.postValue(cycleListSnapshot)
            }
//            cycleListResponse.forEach {
//                deleteCycleData(it)
//            }
        }
//        cycleListSnapshot.addAll(cycleListResponse)
//        cycleList.postValue(cycleListSnapshot)
    }


    fun insertCycleData(cycledata: CycleData) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                cycleDao.insert(cycledata)
            }
        }
    }

    fun updateCycleData(cycledata: CycleData) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                cycleDao.update(cycledata)
            }
        }
    }

    fun deleteCycleData(cycledata: CycleData) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                cycleDao.delete(cycledata)
            }
        }
    }


    fun loadLastCycleData(baseId: Int, lastNumber: Int) {
        coroutineScope.launch {
            val cycleListResponse = fetchPublicTimeline()
            cycleListResponse.forEach {
                if (it.baseId == baseId && it.number_of_cycle == lastNumber) {
                    lastCycleData = it
                    Timber.i("lastCycleData: $lastCycleData")
                } else return@forEach
            }
        }
    }


    suspend fun fetchPublicTimeline() =
        withContext(Dispatchers.IO) {
            cycleDao.getAllData()
        }

}

