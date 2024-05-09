# 🍫 Brownie: Jetpack Compose UI Library 🚀

Brownie is a 🌟 Jetpack Compose library module that provides a set of pre-defined components to accelerate the development of Android interfaces and applications. It is designed to help developers apply best practices in screen state management and utilize the most effective design patterns in their projects.

<p align="center">
  <img src="https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white" />
  <img src="https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" />
  <img src="https://img.shields.io/badge/Material%20UI-007FFF?style=for-the-badge&logo=mui&logoColor=white" />
</p>

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

This setup leverages Brownie's components and ViewModel to accelerate the development of robust features while adhering to best practices in architecture and UI design.

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

Inside the SignInScreen function, there's a BrownieScreen composable. This is a custom screen component provided by the Brownie library. It's responsible for managing the UI state and handling side effects in a declarative way. Here's what each parameter does:

- **viewModel:** The **SignInViewModel** instance passed to the **BrownieScreen** component.
- **onBackPressed:** The lambda function passed to the **BrownieScreen** component, which is called when the user navigates back from the screen.
- **onInitialUiState:** A lambda function that provides the initial UI state for the screen. In this case, it returns a new instance of **SignInUiState.**
- **onSideEffect:** A lambda function that handles side effects triggered by the ViewModel. When a side effect occurs (such as successful authentication or profile selection required), this function is called, and appropriate actions (navigating to home or profile selection) are performed.

Finally, inside the BrownieScreen composable, there's another composable function called **SignInScreenContent.** This is the actual content of the sign-in screen, where UI elements like text fields, buttons, etc., are defined. It takes the current UI state (uiState) as a parameter and callback functions for handling user interactions:

- **uiState:** The current state of the sign-in screen, which is provided by the BrownieScreen component.
- **onEmailChanged:** A callback function for handling changes in the email input field.
- **onPasswordChanged:** A callback function for handling changes in the password input field.
- **onSignIn:** A callback function for handling the sign-in action.

Overall, this implementation follows a declarative approach using Compose and Brownie components, making it easier to manage the UI state and handle user interactions in a clear and concise manner.

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

This is a Composable function named **SignInScreenContent**, responsible for rendering the content of the sign-in screen. It takes the following parameters:

- **uiState:** The current state of the sign-in screen, containing properties like isLoading, email, and password.
- **onEmailChanged:** A callback function that will be invoked when the email input field changes.
- **onPasswordChanged:** A callback function that will be invoked when the password input field changes.
- **onSignIn:** A callback function that will be invoked when the user clicks the sign-in button.

The **BrownieScreenContent** composable is used to create a screen layout. It takes a title resource as a parameter.

Inside the BrownieScreenContent, a BrownieCardColumn composable is used to arrange multiple UI components vertically. This includes text fields for email and password inputs, a button for sign-in, and a divider between them.

Overall, the **SignInScreenContent** composable is responsible for rendering the content of the sign-in screen using Brownie's components. It ensures a consistent UI design and handles user interactions efficiently through callback functions.


