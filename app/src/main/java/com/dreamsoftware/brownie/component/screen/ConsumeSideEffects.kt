package com.dreamsoftware.brownie.component.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun <SE: SideEffect, VM: BrownieViewModel<*, SE>>ConsumeSideEffects(
    viewModel: VM,
    lifecycle: Lifecycle,
    onSideEffectFired: (SE) -> Unit
) {
    RepeatOnStart(viewModel, lifecycle) {
        sideEffectWithReplay.collectLatest { event ->
            onSideEffectFired(event)
        }
    }
    RepeatOnStart(viewModel, lifecycle) {
        sideEffectWithoutReplay.collectLatest { event ->
            onSideEffectFired(event)
        }
    }
}

@Composable
private fun <SE: SideEffect, VM: BrownieViewModel<*, SE>> RepeatOnStart(
    viewModel: VM,
    lifecycle: Lifecycle,
    block: suspend VM.() -> Unit
) {
    LaunchedEffect(viewModel, lifecycle) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.block()
        }
    }
}