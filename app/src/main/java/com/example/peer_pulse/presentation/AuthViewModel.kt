package com.example.peer_pulse.presentation

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()





    fun signUp(){
        viewModelScope.launch {
            authRepository.signUp(email, password).collect {
                _signUp.value = it
            }
        }
    }

/*fun login(email: String,password: String){
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
        .addOnCompleteListener {
            Log.d(TAG,"login_Sucess")
            Log.d(TAG,"${it.isSuccessful}")
            if(it.isSuccessful){
                Log.d(TAG,"yes yes yes")
        }

}.addOnFailureListener {
    Log.d(TAG,"login failed")
        }
}*/
fun login(email: String, password: String) {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Login successful")
                // Handle successful login  navigate to Main Screen)
                //navController.navigate(Screens.MainScreen.route)
            } else {
                Log.d(TAG, "Login failed", task.exception)
                // Handle login failure (e.g., display error message)
                val exception = task.exception

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




    /*suspend fun check(targetEmail: String): Boolean {
        val db=FirebaseFirestore.getInstance()
        val docRef = db.collection("users").document(targetEmail)
        val documentSnapshot = try {
            Log.d("AuthViewModel", "$docRef")
            docRef.get().await()
        } catch (e: FirebaseFirestoreException) {
            return false
        }
        return documentSnapshot.exists()
    }*/


    suspend fun check(targetEmail: String): Boolean {
        val db = FirebaseFirestore.getInstance()
        val query = db.collection("users").whereEqualTo("email", targetEmail).get().await()
       return query.isEmpty
    }
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