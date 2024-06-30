package com.example.peer_pulse.presentation.postUI

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peer_pulse.domain.model.Post
import com.example.peer_pulse.domain.model.Preferences
import com.example.peer_pulse.domain.repository.PostsRepository
import com.example.peer_pulse.utilities.ResponseState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostViewModel @Inject constructor(
    private val postsRepository: PostsRepository,
    private val auth : FirebaseAuth
) : ViewModel() {

    init {
        auth.addAuthStateListener {
            userId = it.currentUser?.uid
        }
    }


    private val _postState = mutableStateOf<ResponseState<Post?>>(ResponseState.Success(null))
    val postState : State<ResponseState<Post?>> = _postState

    private val _savePostState = mutableStateOf<ResponseState<Boolean?>>(ResponseState.Success(null))
    val savePostState: State<ResponseState<Boolean?>> = _savePostState

    private val _deletePostState = mutableStateOf<ResponseState<String>>(ResponseState.Success(""))
    val deletePostState: State<ResponseState<String>> = _deletePostState

    var userId = auth.currentUser?.uid


    fun getPost(postId: String){
        viewModelScope.launch {
            postsRepository.getPost(postId).collect { state ->
                _postState.value = state
            }
        }
    }
    fun savePost(
        title : String,
        description : String,
        images : List<Uri?>,
        preferences : String,
        preferencesId : String
    ){
        val image : List<String> =  images.map { it.toString() }
        viewModelScope.launch {
            postsRepository.savePost(
                title,
                description,
                image,
                preferences,
                preferencesId,
                userId!!
            ).collect{ state ->
                _savePostState.value = state
            }
        }
    }

    fun deletePost(postId: String) {
        viewModelScope.launch {
            postsRepository.deletePost(postId).collect { state ->
                _deletePostState.value = state
            }
        }
    }



}