package com.dreamsoftware.brownieuiexample.domain.usecase.impl

import com.dreamsoftware.brownie.core.BrownieUseCaseWithParams
import com.dreamsoftware.brownieuiexample.domain.model.AuthenticationBO
import kotlinx.coroutines.delay
import java.util.UUID

class SignInUseCase: BrownieUseCaseWithParams<SignInUseCase.Params, AuthenticationBO>() {

    override suspend fun onExecuted(params: Params): AuthenticationBO {
        delay(5000L)
        return AuthenticationBO(uuid = UUID.randomUUID().toString(), username = "ssanchez", token = "", profilesCount = 2)
    }

    data class Params(
        val email: String,
        val password: String
    )
}