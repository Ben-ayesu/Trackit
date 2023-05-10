package com.example.trackit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackit.R
import com.example.trackit.ui.Viewmodels.LoginViewModel

@Composable
fun SignInScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToSignUpPage: () -> Unit,
    onNavToHomePage: () -> Unit,
    modifier: Modifier = Modifier.padding(8.dp)
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null
    val context = LocalContext.current

    val showPassword = remember { mutableStateOf(false) }

    Column(
        modifier
            .fillMaxSize()
            .padding(top = 56.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        Alignment.CenterHorizontally
    ) {
        //Image or Icon
        Image(
            painter = painterResource(id = R.drawable.hotpot1),
            contentDescription = "",
            modifier.clip(shape = CircleShape)
        )
        // Welcome text
        Text(
            text = stringResource(R.string.sign_in_welcome_text),
            modifier.padding(top = 24.dp),
            style = MaterialTheme.typography.headlineMedium,
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
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            maxLines = 1
        )
        // Password Text Field
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
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
            maxLines = 1,
            visualTransformation = if (showPassword.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
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
        // Forgot password
        Box(
            modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            TextButton(
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = "Forgot password?",
                    fontSize = 16.sp
                )
            }
        }
        // Sign in Button
        Button(
            onClick = {
                loginViewModel?.loginUser(context)
            },
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(8.dp)
        ) {
            Text(text = "Sign In")
        }
        // Sign up text
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Don't have an Account?")
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

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SignInScreenPreview() {
    SignInScreen(
        null,
        { },
        { }
    )
}
