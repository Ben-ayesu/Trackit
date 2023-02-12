package com.example.trackit.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trackit.R

@Composable
fun SignInScreen(
    modifier: Modifier =
        Modifier.padding(16.dp)
) {
    Column(
        modifier.fillMaxWidth()
    ) {
        Title()
        Spacer(modifier.height(1.dp))
        EmailTextField()
        Spacer(modifier.height(1.dp))
        PasswordTextField()
        Spacer(modifier.height(1.dp))
        SignInButton()
    }
}

@Composable
fun Title() {
    Text(text = stringResource(R.string.sign_in_welcome_text))
}

@Composable
fun EmailTextField() {
    val emailState = remember {
        mutableStateOf(TextFieldValue())
    }
    TextField(
        value = emailState.value,
        onValueChange = {
            emailState.value = it
        })
}

@Composable
fun PasswordTextField() {
    val passwordState = remember {
        mutableStateOf(TextFieldValue())
    }
    TextField(
        value = passwordState.value,
        onValueChange = {
            passwordState.value = it
        })
}

@Composable
fun SignInButton(){
    Button(
        onClick = { /*TODO*/ }
    ) {
        Text(text = "Sign In")
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen()
}
