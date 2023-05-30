package org.saintgits.edutrack.viewmodel

import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import org.saintgits.edutrack.model.ApiResult
import org.saintgits.edutrack.model.Course
import org.saintgits.edutrack.model.FirebaseCoursesRepository
import org.saintgits.edutrack.model.StudentUser
import org.saintgits.edutrack.model.User
import org.saintgits.edutrack.utils.nthSlotTime
import org.saintgits.edutrack.viewmodel.result.LoadableState
import java.text.DateFormatSymbols
import java.util.*

class StudentTimeTableViewModel(private val user: User): ViewModel() {
    private val courseRecordsRepository = FirebaseCoursesRepository(firestore = FirebaseFirestore.getInstance())
    private val _coursesForDay: MutableLiveData<LoadableState<List<TimeTableElement>>> = MutableLiveData(LoadableState.Loading())
    private val _activeDate: MutableLiveData<Calendar> = MutableLiveData(Calendar.getInstance())
    val coursesForDay: LiveData<LoadableState<List<TimeTableElement>>> = _coursesForDay
    val activeDate: LiveData<Calendar> = _activeDate

    init {
        viewModelScope.launch {
            updateDate(Calendar.getInstance())
        }
    }

    suspend fun updateDate(date: Calendar) {
        _activeDate.postValue(date)
        _coursesForDay.postValue(LoadableState.Loading())
        when (user) {
            is StudentUser -> {
                when (val coursesMap = courseRecordsRepository.fetchCourse(user.courseCodes)) {
                    is ApiResult.Error -> {
                        _coursesForDay.postValue(LoadableState.Error("Failed to load courses"))
                    }
                    is ApiResult.Success -> {
                        val dayNames: Array<String> = DateFormatSymbols().weekdays
                        val dayName = dayNames[date.get(Calendar.DAY_OF_WEEK)] ?: "Monday"
                        val initialPart = when (dayName.lowercase()) {
                            "monday" -> "M"
                            "tuesday" -> "TU"
                            "wednesday" -> "W"
                            "thursday" -> "TH"
                            "friday" -> "F"
                            "saturday" -> "SA"
                            "sunday" -> "SU"
                            else -> "M"
                        }
                        val courses = coursesMap.data.values.filter { it.slot.any { slot -> slot.startsWith(initialPart) } }.flatMap { course ->
                            course.slot.filter { slot -> slot.startsWith(initialPart)  }.map {
                                val idx = it.removePrefix(initialPart).toInt()
                                TimeTableElement(
                                    idx,
                                    course,
                                    it,
                                    nthSlotTime(idx)
                                )
                            }.sortedBy { it.idx }
                        }

                        _coursesForDay.postValue(LoadableState.Result(courses))
                    }
                }
            }
            else -> {
                _coursesForDay.postValue(LoadableState.Error("Non-Student user!"))
            }
        }
    }

    class Factory(private val user: User): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StudentTimeTableViewModel::class.java)) {
               return StudentTimeTableViewModel(user) as T
            }
            return super.create(modelClass)
        }
    }
}

data class TimeTableElement(
    val idx: Int,
    val course: Course,
    val slot: String,
    val time: String
)


