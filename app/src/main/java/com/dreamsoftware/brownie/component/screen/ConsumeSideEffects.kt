package com.dreamsoftware.brownie.component.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect

@Composable
internal fun <SE: SideEffect, VM: BrownieViewModel<*, SE>>ConsumeSideEffects(
    viewModel: VM,
    lifecycle: Lifecycle,
    onSideEffectFired: (SE) -> Unit
) {
    LaunchedEffect(viewModel, lifecycle) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { event ->
                onSideEffectFired(event)
            }
        }
    }
}