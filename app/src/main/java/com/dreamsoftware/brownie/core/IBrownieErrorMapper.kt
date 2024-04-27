package com.dreamsoftware.brownie.core

interface IBrownieErrorMapper {
    fun mapToMessage(ex: Throwable): String
}