package com.dreamsoftware.brownie.utils

import androidx.annotation.StringRes

interface IBrownieApplicationAware {

    fun getString(@StringRes stringResId: Int): String
}