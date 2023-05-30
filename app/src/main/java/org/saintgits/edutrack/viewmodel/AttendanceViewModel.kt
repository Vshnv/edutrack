package org.saintgits.edutrack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import org.saintgits.edutrack.model.ApiResult
import org.saintgits.edutrack.model.AttendanceRecord
import org.saintgits.edutrack.model.Course
import org.saintgits.edutrack.model.FirebaseAttendanceRecordsRepository
import org.saintgits.edutrack.model.FirebaseCoursesRepository
import org.saintgits.edutrack.model.FirebaseUserRepository
import org.saintgits.edutrack.viewmodel.result.LoadableState

class AttendanceViewModel: ViewModel() {
    private val attendanceRecordsRepository = FirebaseAttendanceRecordsRepository(FirebaseFirestore.getInstance())
    private val courseRecordsRepository = FirebaseCoursesRepository(FirebaseFirestore.getInstance())

    private val firebaseUser = FirebaseAuth.getInstance().currentUser

    private val _attendanceRecords = MutableLiveData<LoadableState<Map<Course, AttendanceRecord>>>()
    val attendanceRecord: LiveData<LoadableState<Map<Course, AttendanceRecord>>> = _attendanceRecords

    init {
        viewModelScope.launch {
            firebaseUser?.uid?.let { uid ->
                when (val attendanceResults = attendanceRecordsRepository.fetchAttendanceRecords(uid)) {
                    is ApiResult.Error -> {
                        _attendanceRecords.postValue(LoadableState.Error(attendanceResults.exception?.message ?: "Something went wrong"))
                    }
                    is ApiResult.Success -> {
                        val records = attendanceResults.data
                        when (val coursesResult = courseRecordsRepository.fetchCourse(records.map { it.courseId })) {
                            is ApiResult.Error -> {
                                _attendanceRecords.postValue(LoadableState.Error(coursesResult.exception?.message ?: "Something went wrong"))
                            }
                            is ApiResult.Success -> {
                                val courseMap = coursesResult.data
                                val result: Map<Course, AttendanceRecord> = records.filter { courseMap[it.courseId] != null }.map { courseMap[it.courseId]!! to it }.toMap()
                                _attendanceRecords.postValue(LoadableState.Result(result))
                            }
                        }
                    }
                }
            } ?: kotlin.run {
                _attendanceRecords.postValue(LoadableState.Error("Something went wrong"))
            }
        }
    }
}