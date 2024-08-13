package com.sudheer.aware.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sudheer.aware.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sudheer.aware.components.ButtonComponent
import com.sudheer.aware.components.ClickableLoginTextComponent
import com.sudheer.aware.components.DividerTextComponent
import com.sudheer.aware.components.HeadingTextComponent
import com.sudheer.aware.components.MyTextFieldComponent
import com.sudheer.aware.components.PasswordTextFieldComponent
import com.sudheer.aware.data.LoginViewModel
import com.sudheer.aware.data.login.LoginUIEvent
import com.sudheer.aware.navigation.AppRouter
import com.sudheer.aware.navigation.Screen
import com.sudheer.aware.navigation.SystemBackButtonHandler


@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()) {
    Box(
        modifier = Modifier.fillMaxSize()
            /*  .background(color = Color(R.color.white)),*/
            .background(color = Color(0xFF4CAF50)),
        contentAlignment = Alignment.Center,

        ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(color =  Color(0xFF4CAF50))
                .align(Alignment.Center)
        ) {
            Column(
                modifier = Modifier
                    .background(color = Color(0x2D4CAF50)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {


                HeadingTextComponent(value = "Login")
                Spacer(modifier = Modifier.height(28.dp))


                MyTextFieldComponent(labelValue = stringResource(id = R.string.email),
                    painterResource(id = R.drawable.message),
                    onTextChanged = { loginViewModel.onEvent(LoginUIEvent.EmailChanged(it)) },
                    errorStatus = loginViewModel.loginUIState.value.emailError
                )

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    painterResource(id = R.drawable.lock),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.passwordError
                )

                Spacer(modifier = Modifier.height(40.dp))

                Spacer(modifier = Modifier.height(40.dp))

                ButtonComponent(
                    value = stringResource(id = R.string.login),
                    onButtonClicked = {
                       loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)
                    },
                    isEnabled = loginViewModel.allValidationsPassed.value
                )

                Spacer(modifier = Modifier.height(20.dp))

                DividerTextComponent()

                ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {
                    AppRouter.navigateTo(Screen.SignUpScreen)
                })
            }
        }

        if(loginViewModel.loginInProgress.value) {
            CircularProgressIndicator()
        }
    }

    SystemBackButtonHandler {
        AppRouter.navigateTo(Screen.SignUpScreen)
    }

}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}

