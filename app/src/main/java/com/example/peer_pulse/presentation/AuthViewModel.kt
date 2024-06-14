package com.example.peer_pulse.presentation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peer_pulse.domain.model.colleges
import com.example.peer_pulse.data.login.SignInResult
import com.example.peer_pulse.data.login.SignInState
import com.example.peer_pulse.domain.repository.AuthRepository
import com.example.peer_pulse.domain.repository.collegeRepository
import com.example.peer_pulse.utilities.ResponseState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.regex.Pattern
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val auth : FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val collegeRepository: collegeRepository
) : ViewModel(){

    init {
        auth.addAuthStateListener {
            userId = it.currentUser?.uid
            email = it.currentUser?.email ?: ""
            college = whichCollege(email)
        }
    }
    var userId = auth.currentUser?.uid
    var email : String = ""
    var password : String = ""
    var college : String = ""

    private val _signUp = mutableStateOf<ResponseState<Boolean?>>(ResponseState.Success(null))
    val signUp : State<ResponseState<Boolean?>> = _signUp

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _registerCollege = mutableStateOf<ResponseState<Boolean?>>(ResponseState.Success(null))
    val registerCollege : State<ResponseState<Boolean?>> = _registerCollege


    val isUserAuthenticated get() = authRepository.isUserAuthenticated()


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
    auth.signInWithEmailAndPassword(email, password)
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
        val query = firestore.collection("users").whereEqualTo("email", targetEmail).get().await()
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

    fun whichCollege(email: String) : String{
        val collegeCode1 = email.substring(0,3)
        val collegeCode2 = collegeCode1.uppercase()
        var answer : String = "Not Found"
        colleges.forEach {
            if(it.code == collegeCode2){
                answer = it.name
            }
        }
        return answer
    }
    suspend fun registerCollege(){
        if(college != "Not Found"){
            val query = firestore.collection("colleges").whereEqualTo("name", college).get().await()
            if(query.isEmpty){
                viewModelScope.launch {
                    collegeRepository.registerCollege(college).collect{
                        _registerCollege.value = it
                    }
                }
            }
        }
        else{
            _registerCollege.value = ResponseState.Success(false)
        }
    }

}