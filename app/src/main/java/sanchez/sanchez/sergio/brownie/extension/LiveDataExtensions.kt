package sanchez.sanchez.sergio.brownie.extension

import androidx.lifecycle.MutableLiveData
import sanchez.sanchez.sergio.brownie.models.Resource
import sanchez.sanchez.sergio.brownie.models.ResourceState

/**
 * LiveData Extensions
 * ====================
 */


fun <T> MutableLiveData<Resource<T>>.setSuccess(data: T) =
    postValue(Resource(ResourceState.SUCCESS, data))

fun <T> MutableLiveData<Resource<T>>.setLoading() =
    postValue(Resource(ResourceState.LOADING, value?.data))

fun <T> MutableLiveData<Resource<T>>.setError(message: String? = null) =
    postValue(Resource(ResourceState.ERROR, value?.data, message))