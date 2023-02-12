package com.example.trackit.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Title()
        EmailTextField()
        PasswordTextField()
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
        modifier = Modifier
            .fillMaxWidth(),
        value = emailState.value,
        onValueChange = {
            emailState.value = it
        },
        label = {
            Text(stringResource(R.string.email_hint_label))
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun PasswordTextField() {
    val passwordState = remember {
        mutableStateOf(TextFieldValue())
    }
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
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun SignInButton(){
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = "Sign In")
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen()
}
