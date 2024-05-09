package com.dreamsoftware.brownieuiexample.ui.feature.example

import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownieuiexample.domain.model.AuthenticationBO
import com.dreamsoftware.brownieuiexample.domain.usecase.impl.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signInScreenErrorMapper: SignInScreenSimpleErrorMapper,
): BrownieViewModel<SignInUiState, SignInSideEffects>() {

    override fun onGetDefaultState(): SignInUiState = SignInUiState()

    fun onSignIn() {
        with(uiState.value) {
            executeUseCaseWithParams(
                useCase = signInUseCase,
                params = SignInUseCase.Params(email, password),
                onSuccess = ::onSignInSuccessfully,
                onMapExceptionToState = ::onMapExceptionToState
            )
        }
    }

    fun onEmailChanged(newEmail: String) {
        updateState { it.copy(email = newEmail) }
    }

    fun onPasswordChanged(newPassword: String) {
        updateState { it.copy(password = newPassword) }
    }

    private fun onSignInSuccessfully(authenticationBO: AuthenticationBO) {
        launchSideEffect(if(authenticationBO.profilesCount > 0) {
            SignInSideEffects.ProfileSelectionRequired
        } else {
            SignInSideEffects.AuthenticationSuccessfully
        })
    }

    private fun onMapExceptionToState(ex: Exception, uiState: SignInUiState) =
        uiState.copy(
            error = signInScreenErrorMapper.mapToMessage(ex)
        )
}

data class SignInUiState(
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val email: String = DEMO_USER_EMAIL,
    val password: String = DEMO_USER_PASSWORD
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