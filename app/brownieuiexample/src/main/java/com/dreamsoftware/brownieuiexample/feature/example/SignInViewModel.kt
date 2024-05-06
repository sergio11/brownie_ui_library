package com.dreamsoftware.brownieuiexample.feature.example

import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
): BrownieViewModel<SignInUiState, SignInSideEffects>() {

    override fun onGetDefaultState(): SignInUiState = SignInUiState()

    fun onSignIn() {}

    fun onEmailChanged(newEmail: String) {
        updateState { it.copy(email = newEmail) }
    }

    fun onPasswordChanged(newPassword: String) {
        updateState { it.copy(password = newPassword) }
    }
}

data class SignInUiState(
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val email: String = DEMO_USER_EMAIL,
    val emailError: String = String.EMPTY,
    val password: String = DEMO_USER_PASSWORD,
    val passwordError: String = String.EMPTY
): UiState<SignInUiState>(isLoading, error) {
    override fun copyState(isLoading: Boolean, error: String?): SignInUiState =
        copy(isLoading = isLoading, error = error)
}

sealed interface SignInSideEffects: SideEffect {
    data object AuthenticationSuccessfully: SignInSideEffects
    data object ProfileSelectionRequired: SignInSideEffects
}

private const val DEMO_USER_EMAIL = "ssanchez@example.com"
private const val DEMO_USER_PASSWORD = "PasswOrd!11"