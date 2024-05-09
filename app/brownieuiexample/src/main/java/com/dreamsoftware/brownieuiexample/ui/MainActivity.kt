package com.dreamsoftware.brownieuiexample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dreamsoftware.brownie.theme.BrownieTheme
import com.dreamsoftware.brownieuiexample.ui.feature.example.SignInScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BrownieTheme {
                SignInScreen(
                    onGoToHome = {},
                    onGoToProfileSelector = {},
                    onBackPressed = {}
                )
            }
        }
    }
}