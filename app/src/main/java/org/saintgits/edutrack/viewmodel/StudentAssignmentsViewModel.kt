package org.saintgits.edutrack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import org.saintgits.edutrack.model.ApiResult
import org.saintgits.edutrack.model.Assignment
import org.saintgits.edutrack.model.FirebaseAssignmentsRepository
import org.saintgits.edutrack.model.StudentUser
import org.saintgits.edutrack.model.User
import org.saintgits.edutrack.viewmodel.result.LoadableState

class StudentAssignmentsViewModel(val user: User): ViewModel() {
    private val assignmentsRepository = FirebaseAssignmentsRepository(FirebaseFirestore.getInstance())
    private val _availableAssignments: MutableLiveData<LoadableState<List<Assignment>>> = MutableLiveData(LoadableState.Loading())
    val availableAssignments: LiveData<LoadableState<List<Assignment>>> = _availableAssignments

    init {
        viewModelScope.launch {
            if (user !is StudentUser) return@launch
            val courseCodes = user.courseCodes
            when (val assignmentsResult = assignmentsRepository.fetchAssignments(courseCodes)) {
                is ApiResult.Error -> {
                    _availableAssignments.postValue(LoadableState.Error(assignmentsResult.exception?.message ?: "Something went wrong"))
                }
                is ApiResult.Success -> {
                    _availableAssignments.postValue(LoadableState.Result(assignmentsResult.data))
                }
            }
        }
    }

    class Factory(private val user: User): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StudentAssignmentsViewModel::class.java)) {
                return StudentAssignmentsViewModel(user) as T
            }
            return super.create(modelClass)
        }
    }
}