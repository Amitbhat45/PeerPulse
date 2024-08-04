package com.example.peer_pulse.presentation.preferences

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.peer_pulse.data.room.post
import com.example.peer_pulse.domain.repository.PagesRepository
import com.example.peer_pulse.utilities.ResponseState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val auth : FirebaseAuth,
    private val pagesRepository: PagesRepository
) : ViewModel(){

    init {
        auth.addAuthStateListener {
            userId = it.currentUser?.uid
        }
    }

    private val _preferencesPostId = mutableStateOf<ResponseState<List<String>?>>(ResponseState.Success(null))
    val preferences : State<ResponseState<List<String>?>> = _preferencesPostId

    private val _TopicpageFeed = MutableStateFlow<PagingData<post>>(PagingData.empty())
    val TopicpageFeed: StateFlow<PagingData<post>> get() = _TopicpageFeed

    var userId = auth.currentUser?.uid

    fun getPostByPreference(preferenceId : String){
       viewModelScope.launch {
           pagesRepository.getPostByPreference(preferenceId).collect { state ->
               _preferencesPostId.value = state
           }
       }
    }

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            val userPreferences = listOf(
                "Software Engineering",
            )
            pagesRepository.getPostbyTopic(userPreferences)
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _TopicpageFeed.value = pagingData
                }
        }
    }

    fun refreshFeed() {
        fetchPosts()
    }



}