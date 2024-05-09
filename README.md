# 🍫 Brownie: Jetpack Compose UI Library 🚀

Brownie is a 🌟 Jetpack Compose library module that provides a set of pre-defined components to accelerate the development of Android interfaces and applications. It is designed to help developers apply best practices in screen state management and utilize the most effective design patterns in their projects.

## Features 🎉

- **Pre-defined Components:** Brownie offers a variety of ready-to-use components, from buttons to lists and cards, to facilitate the creation of attractive and consistent user interfaces.
- **State Management:** Facilitates screen state management by implementing patterns such as MVI (Model-View-Intent) or MVVM (Model-View-ViewModel).
- **Customization:** Brownie components are highly customizable and can easily adapt to the visual style of any application.
- **Jetpack Compose Compatibility:** Fully integrated with Jetpack Compose, the modern Android UI library, to ensure optimal performance and a smooth development experience.

## Model-View-Intent (MVI) Architecture 🏗️

Brownie encourages the use of the Model-View-Intent (MVI) architecture pattern for effective screen state management. In this pattern:

- **Model:** Represents the state of the UI. Brownie provides a `BrownieViewModel` class that extends from `ViewModel` and handles the UI state.
- **View:** Renders the UI based on the state provided by the ViewModel. Brownie components are seamlessly integrated with Compose to create a declarative UI.
- **Intent:** Represents user actions or events that trigger state changes. Brownie's components, like buttons and text fields, are designed to emit these intents efficiently.

Here's an example of how you can use Brownie components in a screen following the MVI pattern:

```kotlin
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
```

```kotlin
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
        titleRes = R.string.signin_main_title_text
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
```
