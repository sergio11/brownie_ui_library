package com.dreamsoftware.brownieuiexample.ui.feature.example

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    onGoToHome: () -> Unit,
    onGoToProfileSelector: () -> Unit,
    onBackPressed: () -> Unit,
) {
    BrownieScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { SignInUiState() },
        onSideEffect = {
            when(it) {
                SignInSideEffects.AuthenticationSuccessfully -> onGoToHome()
                SignInSideEffects.ProfileSelectionRequired -> onGoToProfileSelector()
            }
        }
    ) { uiState ->
        SignInScreenContent(
            uiState = uiState,
            onEmailChanged = viewModel::onEmailChanged,
            onPasswordChanged = viewModel::onPasswordChanged,
            onSignIn = viewModel::onSignIn
        )
    }
}