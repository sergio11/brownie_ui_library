package com.dreamsoftware.brownieuiexample.domain.model

data class AuthenticationBO(
    val uuid: String,
    val username: String,
    val token: String,
    val profilesCount: Int
)
