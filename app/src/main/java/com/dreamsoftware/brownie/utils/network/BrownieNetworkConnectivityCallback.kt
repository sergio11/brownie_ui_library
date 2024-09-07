package com.dreamsoftware.brownie.utils.network

import android.net.ConnectivityManager
import android.net.Network
import com.dreamsoftware.brownie.utils.BrownieEventBus
import com.dreamsoftware.brownie.utils.IBrownieAppEvent

class BrownieNetworkConnectivityCallback(private val appEventBus: BrownieEventBus) : ConnectivityManager.NetworkCallback() {

    private var lastState: Boolean = true

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        updateConnectivityState(true)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        updateConnectivityState(false)
    }

    private fun updateConnectivityState(newState: Boolean) {
        if (newState != lastState) {
            appEventBus.send(
                IBrownieAppEvent.NetworkConnectivityStateChanged(
                    lastState = lastState,
                    newState = newState
                )
            ).also {
                lastState = newState
            }
        }
    }

    companion object {
        private const val TAG = "NetworkConnectivity"
    }
}