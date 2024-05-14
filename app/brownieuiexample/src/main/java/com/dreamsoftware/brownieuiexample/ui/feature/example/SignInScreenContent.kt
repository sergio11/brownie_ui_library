package com.dreamsoftware.brownieuiexample.ui.feature.example

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieButton
import com.dreamsoftware.brownie.component.BrownieCardColumn
import com.dreamsoftware.brownie.component.BrownieContentDivider
import com.dreamsoftware.brownie.component.BrownieDefaultTextField
import com.dreamsoftware.brownie.component.BrownieLoadingDialog
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextFieldPassword
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.brownie.component.screen.BrownieScreenContent
import com.dreamsoftware.brownie.theme.BrownieTheme
import com.dreamsoftware.brownieuiexample.R

@Composable
fun SignInScreenContent(
    uiState: SignInUiState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSignIn: () -> Unit
) {
    BrownieLoadingDialog(
        isShowingDialog = uiState.isLoading,
        mainLogoRes = R.drawable.ic_launcher_foreground,
        titleRes = R.string.loading_dialog_title,
        descriptionRes = R.string.loading_dialog_description
    )
    BrownieScreenContent(
        titleRes = R.string.signin_main_title_text,
        hasTopBar = false,
        backgroundRes = R.drawable.background_screen
    ) {
        BrownieText(
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 40.dp),
            type = BrownieTextTypeEnum.HEADLINE_LARGE,
            titleRes = R.string.onboarding_subtitle_text,
            textBold = true
        )
        Spacer(modifier = Modifier.weight(1f))
        BrownieCardColumn {
            BrownieText(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp),
                type = BrownieTextTypeEnum.TITLE_MEDIUM,
                titleRes = R.string.signin_secondary_title_text,
                textAlign = TextAlign.Center
            )
            BrownieDefaultTextField(
                labelRes = R.string.signin_input_email_label,
                placeHolderRes = R.string.signin_input_email_placeholder,
                keyboardType = KeyboardType.Email,
                value = uiState.email,
                onValueChanged = onEmailChanged
            )
            BrownieTextFieldPassword(
                labelRes = R.string.signin_input_password_label,
                placeHolderRes = R.string.signin_input_password_placeholder,
                value = uiState.password,
                onValueChanged = onPasswordChanged
            )
            BrownieContentDivider()
            BrownieButton(
                text = R.string.signin_login_button_text,
                onClick = onSignIn
            )
            Spacer(modifier = Modifier.padding(bottom = 10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenContentPreview() {
    BrownieTheme {
        SignInScreenContent(
            uiState = SignInUiState(),
            onEmailChanged = {},
            onPasswordChanged = {},
            onSignIn = {}
        )
    }
}