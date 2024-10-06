package com.example.peer_pulse.presentation.community

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.peer_pulse.data.room.post
import com.example.peer_pulse.domain.model.Community
import com.example.peer_pulse.domain.model.Message
import com.example.peer_pulse.domain.model.colleges
import com.example.peer_pulse.domain.repository.CommunityRepository
import com.example.peer_pulse.utilities.ResponseState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val communityRepository: CommunityRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    var userId: String? = auth.currentUser?.uid

    init {
        auth.addAuthStateListener {
            userId = it.currentUser?.uid
            email = it.currentUser?.email ?: ""
            college = whichCollege(email)
        }
    }

    var email = ""
    var college = ""

    private val _clgPosts = MutableStateFlow<PagingData<post>>(PagingData.empty())
    val clgPosts: StateFlow<PagingData<post>> get() = _clgPosts

    private val _allCommunityList = mutableStateOf<ResponseState<List<Community>>>(ResponseState.Success(emptyList()))
    val allCommunityList: State<ResponseState<List<Community>>> = _allCommunityList

    private val _messagesByCommunity = mutableStateOf<ResponseState<List<Message>>>(ResponseState.Success(emptyList()))
    val messagesByCommunity: State<ResponseState<List<Message>>> = _messagesByCommunity

    private val _sendMessage = mutableStateOf<ResponseState<Boolean>>(ResponseState.Success(false))
    val sendMessage: State<ResponseState<Boolean>> = _sendMessage

    private val _deleteMessage = mutableStateOf<ResponseState<Boolean>>(ResponseState.Success(false))
    val deleteMessage: State<ResponseState<Boolean>> = _deleteMessage

    private val _generalPosts = mutableStateOf<ResponseState<List<String>>>(ResponseState.Success(emptyList()))
    val generalPosts : State<ResponseState<List<String>>> = _generalPosts


    var communityList: List<Community> = emptyList()

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            communityRepository.getPosts(emptyList(),college)
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _clgPosts.value = pagingData
                }
        }
    }

    fun getAllCommunities(collegeCode: String) {
        viewModelScope.launch {
            communityRepository.getAllCommunities(collegeCode).collect { state ->
                _allCommunityList.value = state
                if (state is ResponseState.Success) {
                    communityList = state.data
                }
            }
        }
    }

    fun getMessagesByCommunity(collegeCode: String, communityId: String) {
        viewModelScope.launch {
            communityRepository.getMessagesByCommunity(collegeCode, communityId).collect { state ->
                _messagesByCommunity.value = state
            }
        }
    }

    fun sendMessage(
        text: String,
        imageUrl: String,
        userId: String,
        communityName: String,
        collegeCode: String,
        timeStamp: Long,
        userName : String
    ) {
        viewModelScope.launch {
            communityRepository.sendMessage(
                text = text,
                imageUrl = imageUrl,
                userId = userId,
                communityName = communityName,
                collegeCode = collegeCode,
                timeStamp = timeStamp,
                userName = userName
            ).collect { state ->
                _sendMessage.value = state
            }
        }
    }

    fun deleteMessage(messageId: String) {
        viewModelScope.launch {
            communityRepository.deleteMessage(messageId).collect { state ->
                _deleteMessage.value = state
            }
        }
    }

    fun getGeneralPostsByCollege(collegeName: String){
        viewModelScope.launch {
            communityRepository.getGeneralPostIdsByCollege(collegeName).collect{state ->
                _generalPosts.value = state
            }
        }
    }

    fun resetDeleteState() {
        _deleteMessage.value = ResponseState.Success(false)
    }

    fun resetSendState() {
        _sendMessage.value = ResponseState.Success(false)
    }
    private fun whichCollege(email: String) : String{
        if (email.length < 3) return ""
        val collegeCode1 = email.substring(0,3)
        val collegeCode2 = collegeCode1.uppercase()
        var answer = "Not Found"
        colleges.forEach {
            if(it.code == collegeCode2){
                answer = it.name
            }
        }
        return answer
    }
}