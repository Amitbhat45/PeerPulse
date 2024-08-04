package com.example.peer_pulse.presentation.community

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peer_pulse.domain.model.Community
import com.example.peer_pulse.domain.repository.CommunityRepository
import com.example.peer_pulse.utilities.ResponseState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val communityRepository: CommunityRepository,
    private val auth : FirebaseAuth
) : ViewModel() {

    var userId : String? = auth.currentUser?.uid
    init {
        auth.addAuthStateListener {
            userId = it.currentUser?.uid
        }
    }


    private val _allCommunityList = mutableStateOf<ResponseState<List<Community>>>(ResponseState.Success(emptyList()))
    val allCommunityList : State<ResponseState<List<Community>>> = _allCommunityList

    var communityList : List<Community> = emptyList()


    fun getAllCommunities(collegeCode : String){
        viewModelScope.launch {
            communityRepository.getAllCommunities(collegeCode).collect { state ->
                _allCommunityList.value = state
                if( state is ResponseState.Success){
                    communityList = state.data
                }
            }
        }
    }



}