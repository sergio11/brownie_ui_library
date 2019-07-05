package sanchez.sanchez.sergio.brownie.ui.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception

abstract class SupportLCEViewModel<T, E>: SupportViewModel() {

    val dataList: MutableLiveData<List<T>> by lazy {
        MutableLiveData<List<T>>()
    }

    val error: MutableLiveData<Exception> by lazy {
        MutableLiveData<Exception>()
    }

    val status: MutableLiveData<SupportLCEOperationResultEnum> by lazy {
        MutableLiveData<SupportLCEOperationResultEnum>()
    }

    fun loadData(params: E?) = viewModelScope.launch {
        try {
            onLoading()
            val dataList = params?.let {
                onLoadData(it)
            } ?: onLoadData()
            if(dataList.isNotEmpty())
                onDataFound(dataList)
            else
                onDataNotFound()
        } catch (ex: Exception) {
            onError(ex)
        }
    }

    @Throws(Exception::class)
    abstract suspend fun onLoadData(): List<T>

    @Throws(Exception::class)
    abstract suspend fun onLoadData(params: E): List<T>

    /**
     * Private Methods
     */

    private fun onDataFound(data: List<T>) {
        this.dataList.postValue(data)
        this.status.postValue(SupportLCEOperationResultEnum.DATA_FOUND)
    }

    private fun onDataNotFound() {
        this.dataList.postValue(ArrayList())
        this.status.postValue(SupportLCEOperationResultEnum.NO_DATA_FOUND)
    }

    private fun onError(ex: Exception) {
        this.dataList.postValue(ArrayList())
        this.error.postValue(ex)
        this.status.postValue(SupportLCEOperationResultEnum.ERROR)
    }

    private fun onLoading() {
        this.dataList.postValue(ArrayList())
        this.status.postValue(SupportLCEOperationResultEnum.LOADING)
    }

}