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
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val auth : FirebaseAuth,
    private val pagesRepository: PagesRepository,
    private val firestore: FirebaseFirestore
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

    private val _mostLikedLastWeek = MutableStateFlow<PagingData<post>>(PagingData.empty())
    val mostLikedLastWeek: StateFlow<PagingData<post>> get() = _mostLikedLastWeek

    private val _mostLikedLastMonth = MutableStateFlow<PagingData<post>>(PagingData.empty())
    val mostLikedLastMonth: StateFlow<PagingData<post>> get() = _mostLikedLastMonth

    private val _mostLikedLastYear = MutableStateFlow<PagingData<post>>(PagingData.empty())
    val mostLikedLastYear: StateFlow<PagingData<post>> get() = _mostLikedLastYear

    private var _preferenceId: List<String> = emptyList()

    var userId = auth.currentUser?.uid

    fun getPostByPreference(preferenceId : String){
       viewModelScope.launch {
           pagesRepository.getPostByPreference(preferenceId).collect { state ->
               _preferencesPostId.value = state
           }
       }
    }

    init {
        fetchPosts(_preferenceId)
    }
    fun setPreferences(preferenceId: List<String>) {
        _preferenceId = preferenceId
        fetchPosts(preferenceId)
    }

    fun fetchPosts(preferenceId : List<String>) {
        viewModelScope.launch {
            pagesRepository.getPostbyTopic(preferenceId)
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _TopicpageFeed.value = pagingData
                }
        }
    }

    fun refreshFeed() {
        fetchPosts(listOf("\"Software Engineering\""))
    }

    fun fetchMostLikedLastWeek(preferences1:List<String>) {
        viewModelScope.launch {

           pagesRepository.getMostLikedPostsLastWeek(preferences1)
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _mostLikedLastWeek.value = pagingData
                }
        }
    }

    fun fetchMostLikedLastMonth(preferences1:List<String>) {
        viewModelScope.launch {

            pagesRepository.getMostLikedPostsLastMonth(preferences1)
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _mostLikedLastMonth.value = pagingData
                }
        }
    }

    fun fetchMostLikedLastYear(preferences1:List<String>) {
        viewModelScope.launch {
            pagesRepository.getMostLikedPostsLastYear(preferences1)
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _mostLikedLastYear.value = pagingData
                }
        }
    }


}