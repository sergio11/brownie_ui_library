package com.dreamsoftware.brownieuiexample.ui.feature.example

import android.content.Context
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.brownieuiexample.R

class SignInScreenSimpleErrorMapper(
    private val context: Context
): IBrownieErrorMapper {
    override fun mapToMessage(ex: Throwable): String = context.getString(R.string.generic_error_exception)
}