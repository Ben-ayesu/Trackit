package com.example.trackit.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trackit.R

@Composable
fun SignInScreen(
    modifier: Modifier =
        Modifier.padding(16.dp)
) {
    val context = LocalContext.current
    Column(
        modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Title()
        EmailTextField()
        PasswordTextField()
        SignInButton(context)
    }
}

@Composable
fun Title() {
    Text(
        text = stringResource(R.string.sign_in_welcome_text),
        style = MaterialTheme.typography.headlineMedium
    )
}
@Composable
fun EmailTextField() {
    val emailState = remember {
        mutableStateOf(TextFieldValue())
    }
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = emailState.value,
        onValueChange = { emailState.value = it },
        label = { Text(stringResource(R.string.email_hint_label)) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}

@Composable
fun PasswordTextField() {
    val passwordState = remember { mutableStateOf(TextFieldValue()) }
    val showPassword = remember { mutableStateOf(false) }

    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = passwordState.value,
        onValueChange = {
            passwordState.value = it
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
}

@Composable
fun SignInButton(context: Context) {
    Button(
        onClick = { Toast.makeText(context, "This button works", Toast.LENGTH_SHORT).show() },
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(text = "Sign In")
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen()
}
