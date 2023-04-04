package com.example.trackit.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.trackit.R

@Composable
fun SignInScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToSignUpPage: () -> Unit,
    onNavToHomePage: () -> Unit,
    modifier: Modifier =
        Modifier.padding(16.dp)
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null
    val context = LocalContext.current

    val showPassword = remember { mutableStateOf(false) }

    Column(
        modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome text
        Text(
            text = stringResource(R.string.sign_in_welcome_text),
            style = MaterialTheme.typography.headlineMedium
        )
        // Login Text
        Text(
            text = stringResource(R.string.login_text),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary
        )

        if (isError) {
            Text(
                text = loginUiState?.loginError ?: "unknown error",
                color = Color.Red
            )
        }
        // Email Text Field
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = loginUiState?.userName ?: "",
            onValueChange = { loginViewModel?.onUserNameChange(it) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            },
            label = { Text(stringResource(R.string.email_hint_label)) },
            isError = isError,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        // Password Text Field
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = loginUiState?.password ?: "",
            onValueChange = {
                loginViewModel?.onPasswordNameChange(it)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.password_hint_label))
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            visualTransformation = if (showPassword.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                if (showPassword.value) {
                    IconButton(
                        onClick = { showPassword.value = false }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = stringResource(R.string.hide_password)
                        )
                    }
                } else {
                    IconButton(
                        onClick = {
                            showPassword.value = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = stringResource(R.string.show_password)
                        )
                    }
                }
            }
        )
        // Sign in Button
        Button(
            onClick = {
                loginViewModel?.loginUser(context)
            },
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = "Sign In")
        }
        //
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Don't have an Account?")
            Spacer(modifier = Modifier.size(8.dp))
            TextButton(
                onClick = {
                    onNavToSignUpPage.invoke()
                }
            ) {
                Text(text = "Sign Up")
            }
        }

        if (loginUiState?.isLoading == true) {
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = loginViewModel?.hasUser) {
            if (loginViewModel?.hasUser == true) {
                onNavToHomePage.invoke()
            }
        }
    }
}

//@Preview(
//    showBackground = true,
//    showSystemUi = true
//)
//@Composable
//fun SignInScreenPreview() {
//    val nav = rememberNavController()
//    SignInScreen(null, nav)
//}
