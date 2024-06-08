package com.example.peer_pulse.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peer_pulse.data.AuthRepositoryImpl
import com.example.peer_pulse.domain.repository.AuthRepository
import com.example.peer_pulse.utilities.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
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
}