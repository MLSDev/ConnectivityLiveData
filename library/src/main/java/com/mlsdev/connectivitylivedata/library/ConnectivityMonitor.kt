package com.mlsdev.connectivitylivedata.library

import android.annotation.TargetApi
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build

sealed class ConnectivityMonitor(protected val application: Application) {

    protected val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    protected var callbackFunction: ((Boolean) -> Unit) = {}

    abstract fun startListening(callback: (Boolean) -> Unit)

    abstract fun stopListening()

    @TargetApi(Build.VERSION_CODES.N)
    private class NougatConnectivityMonitor(application: Application) : ConnectivityMonitor(application) {

        private val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                callbackFunction(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                callbackFunction(false)
            }
        }

        override fun startListening(callback: (Boolean) -> Unit) {
            callbackFunction = callback
            callbackFunction(false)
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        }

        override fun stopListening() {
            connectivityManager.unregisterNetworkCallback(networkCallback)
            callbackFunction = {}
        }
    }

    @Suppress("Deprecation")
    private class LegacyConnectivityMonitor(application: Application) : ConnectivityMonitor(application) {

        private val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)

        private val isNetworkConnected: Boolean
        get() = connectivityManager.activeNetworkInfo?.isConnected == true

        override fun startListening(callback: (Boolean) -> Unit) {
            callbackFunction = callback
            callbackFunction(isNetworkConnected)
            application.registerReceiver(receiver, filter)
        }

        override fun stopListening() {
            application.unregisterReceiver(receiver)
            callbackFunction = {}
        }

        private val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                callbackFunction(isNetworkConnected)
            }
        }
    }

    companion object {
        fun getInstance(application: Application): ConnectivityMonitor =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NougatConnectivityMonitor(application)
            } else {
                LegacyConnectivityMonitor(application)
            }
    }

}