package com.example.trackit.ui.screens

import androidx.lifecycle.ViewModel
import com.example.trackit.repository.AuthRepository

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

}

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val userNameSignUp: String = "",
    val passwordSignUp: String = "",
)