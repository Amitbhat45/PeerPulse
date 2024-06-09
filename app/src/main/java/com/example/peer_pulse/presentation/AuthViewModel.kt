package com.example.peer_pulse.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peer_pulse.data.AuthRepositoryImpl
import com.example.peer_pulse.data.log_in.SignInResult
import com.example.peer_pulse.data.log_in.SignInState
import com.example.peer_pulse.domain.repository.AuthRepository
import com.example.peer_pulse.utilities.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.checkerframework.checker.regex.qual.Regex
import retrofit2.Response
import java.util.concurrent.Flow
import java.util.regex.Pattern
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(){

    var email : String = ""
    var password : String = ""

    private val _signUp = mutableStateOf<ResponseState<Boolean?>>(ResponseState.Success(null))
    val signUp : State<ResponseState<Boolean?>> = _signUp

    private val _signInState = MutableStateFlow(SignInState())
    val signInState = _signInState.asStateFlow()

    fun signUp(){
        viewModelScope.launch {
            authRepository.signUp(email, password).collect {
                _signUp.value = it
            }
        }
    }

    fun emailValidator(email : String) : Boolean{
        val emailRegex = Regex("^\\d[a-zA-Z]{2}\\d{2}[a-zA-Z]{2}\\d{3}\\.[a-z]{2}@[a-z]+\\.edu\\.in$")
        return emailRegex.matches(email)
    }

    fun passwordVerify(first : String, second : String) : Boolean{
        return first == second
    }

    fun passwordValidate(password : String) : Boolean{
        val passwordPattern = Pattern.compile(
            "^(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,32}$"
        )
        return passwordPattern.matcher(password).matches()
    }



    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    fun resetState() {
        _state.update { SignInState() }
    }


}