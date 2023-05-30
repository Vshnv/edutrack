package org.saintgits.edutrack.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudentAssignmentsViewModel: ViewModel() {
    private val _availableAssignments: MutableLiveData<List<Assign>>
}