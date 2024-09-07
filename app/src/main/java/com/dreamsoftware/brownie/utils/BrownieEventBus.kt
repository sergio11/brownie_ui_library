package com.dreamsoftware.brownie.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class BrownieEventBus {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val _events = MutableSharedFlow<IBrownieAppEvent>()
    val events = _events.asSharedFlow()

    fun send(event: IBrownieAppEvent) {
        coroutineScope.launch {
            _events.emit(event)
        }
    }
}

interface IBrownieAppEvent {
    data class NetworkConnectivityStateChanged(val lastState: Boolean, val newState: Boolean): IBrownieAppEvent
}