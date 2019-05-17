package com.mlsdev.connectivitylivedata.sample

import android.app.Application
import androidx.lifecycle.*
import com.mlsdev.connectivitylivedata.library.ConnectivityLiveData
import com.mlsdev.connectivitylivedata.library.ConnectivityState

class MainViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    private val connectivityLiveData = ConnectivityLiveData(application)

    fun getConnectivity(): LiveData<ConnectivityState> =
        Transformations.switchMap(connectivityLiveData) { connectivityState ->
            MutableLiveData<ConnectivityState>().apply { value = connectivityState }
        }
}