package com.example.peer_pulse.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peer_pulse.domain.repository.UserRepository
import com.example.peer_pulse.utilities.ResponseState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val userRepository: UserRepository,
) : ViewModel() {
    init {
        auth.addAuthStateListener {
            userId = it.currentUser?.uid
            getUsername(userId!!)
        }
    }

    private val _state1 =
        mutableStateOf<ResponseState<List<String>?>>(ResponseState.Success(emptyList()))
    val state1: State<ResponseState<List<String>?>> = _state1

    private val _state2 =
        mutableStateOf<ResponseState<List<String>?>>(ResponseState.Success(emptyList()))
    val state2: State<ResponseState<List<String>?>> = _state2

    private val _state3 =
        mutableStateOf<ResponseState<List<String>?>>(ResponseState.Success(emptyList()))
    val state3: State<ResponseState<List<String>?>> = _state3

    private val _state4 = mutableStateOf<ResponseState<Boolean?>>(ResponseState.Success(null))
    val state4: State<ResponseState<Boolean?>> = _state4

    private val _state5 = mutableStateOf<ResponseState<Boolean?>>(ResponseState.Success(null))
    val state5: State<ResponseState<Boolean?>> = _state5

    private val _state6 = mutableStateOf<ResponseState<String?>>(ResponseState.Success(null))
    val state6: State<ResponseState<String?>> = _state6


    var userId = auth.currentUser?.uid
    var userName = ""
    var postIds: List<String?> = emptyList()
    var bookmarkedPostIds: List<String?> = emptyList()
    var followingPageIds: List<String?> = emptyList()

    fun myPosts() {
        userId?.let {
            viewModelScope.launch {
                userRepository.myPosts(it).collect { state ->
                    _state1.value = state
                    if (state is ResponseState.Success) {
                        postIds = state.data
                    }
                }
            }
        }
    }


    fun getBookmarkedPosts() {
        userId?.let {
            viewModelScope.launch {
                userRepository.bookmarkedPosts(it).collect { state ->
                    _state2.value = state
                    if (state is ResponseState.Success) {
                        bookmarkedPostIds = state.data
                    }
                }
            }
        }
    }

    fun getFollowingPages() {
        userId?.let {
            viewModelScope.launch {
                userRepository.followingPages(it).collect { state ->
                    _state3.value = state
                    if (state is ResponseState.Success) {
                        followingPageIds = state.data
                    }
                }
            }
        }
    }

    private fun updateFollowingPages(followingPageNames: List<String?>) {
        userId?.let {
            viewModelScope.launch {
                userRepository.updateFollowingPages(it, followingPageNames).collect { state ->
                    _state4.value = state
                }
            }
        }
    }

    fun addToFollowingPage(name: String) {
        val newFollowingPages = followingPageIds + name
        updateFollowingPages(newFollowingPages)
    }

    fun removeFromFollowingPage(name: String) {
        val newFollowingPages = followingPageIds - name
        updateFollowingPages(newFollowingPages)
    }

    fun updateUsername(username: String) {
        userId?.let {
            viewModelScope.launch {
                userRepository.updateUsername(it, username).collect { state ->
                    _state5.value = state
                }
            }
        }
    }

    private fun getUsername(userId: String) {
        viewModelScope.launch {
            userRepository.getUsername(userId).collect { state ->
                _state6.value = state
                if (state is ResponseState.Success) {
                    userName = state.data
                }
            }
        }
    }


    fun resetState() {
        _state1.value = ResponseState.Success(emptyList())
        _state2.value = ResponseState.Success(emptyList())
        _state3.value = ResponseState.Success(emptyList())
        _state4.value = ResponseState.Success(null)
        _state5.value = ResponseState.Success(null)
    }

}