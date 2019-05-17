package com.mlsdev.connectivitylivedata.library

import android.app.Application
import androidx.lifecycle.MutableLiveData

class ConnectivityLiveData(application: Application) : MutableLiveData<ConnectivityState>() {

    private val connectionMonitor = ConnectivityMonitor.getInstance(application)

    override fun onActive() {
        super.onActive()
        connectionMonitor.startListening(::setConnected)
    }

    override fun onInactive() {
        connectionMonitor.stopListening()
        super.onInactive()
    }

    private fun setConnected(isConnected: Boolean) =
        postValue(if (isConnected) ConnectivityState.CONNECTED else ConnectivityState.DISCONNECTED)

}

enum class ConnectivityState {
    CONNECTED,
    DISCONNECTED
}