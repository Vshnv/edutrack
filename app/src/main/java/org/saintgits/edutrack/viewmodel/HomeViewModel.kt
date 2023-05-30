package org.saintgits.edutrack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import org.saintgits.edutrack.model.ApiResult
import org.saintgits.edutrack.model.FirebaseUserRepository
import org.saintgits.edutrack.model.User
import org.saintgits.edutrack.viewmodel.result.LoadableState

class HomeViewModel: ViewModel() {
    private val userRepository = FirebaseUserRepository(FirebaseFirestore.getInstance())
    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    private val _user = MutableLiveData<LoadableState<User>>()
    val user: LiveData<LoadableState<User>> = _user

    init {
        viewModelScope.launch {
            firebaseUser?.uid?.let { uid ->
                when (val userResult = userRepository.fetchUser(uid)) {
                    is ApiResult.Error -> {
                        _user.postValue(LoadableState.Error(userResult.exception?.message ?: "Something went wrong"))
                    }
                    is ApiResult.Success -> {
                        _user.postValue(LoadableState.Result(userResult.data))
                    }
                }
            } ?: kotlin.run {
                _user.postValue(LoadableState.Error("Invalid User"))
            }
        }
    }
}