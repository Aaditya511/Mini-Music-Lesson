package com.example.minimusiclesson.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minimusiclesson.core.network.ApiResponse
import com.example.minimusiclesson.core.network.NetworkUtils
import com.example.minimusiclesson.core.utils.callApi
import com.example.minimusiclesson.data.model.Lesson
import com.example.minimusiclesson.data.repository.Repository
import com.example.minimusiclesson.presentation.common.ScreenEvents
import com.example.minimusiclesson.presentation.common.UIState
import com.example.minimusiclesson.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val repository: Repository,
    private val networkChecker: NetworkUtils
) : ViewModel() {

    private val _uploadState = MutableStateFlow<UploadState>(UploadState.Idle)
    val uploadState: StateFlow<UploadState> = _uploadState.asStateFlow()
    private val _screenState =
        MutableStateFlow<UIState<List<Lesson>>>(UIState.InitialState)
    val screenState: StateFlow<UIState<List<Lesson>>> = _screenState.asStateFlow()

    private val _commonScreensEvents = MutableSharedFlow<ScreenEvents>()
    val commonScreensEvents: SharedFlow<ScreenEvents> = _commonScreensEvents.asSharedFlow()


    init {
        getLessons()
    }

    private fun getLessons() {
        viewModelScope.launch {

            _screenState.value = UIState.Loading

            val result = callApi(networkChecker = networkChecker) { repository.getLessons() }
            when (result) {
                is ApiResponse.Success -> {
                    val lessonsList = result.data["lessons"] ?: emptyList()
                    if (lessonsList.isEmpty()) {
                        _screenState.value = UIState.Error("No lessons available")
                        _commonScreensEvents.emit(
                            ScreenEvents.ShowSnackbarEvent("No lessons available")
                        )
                    } else {
                        _screenState.value = UIState.Success(lessonsList)
                    }
                }

                is ApiResponse.Error -> {
                    _screenState.value = UIState.Error(result.message)
                    _commonScreensEvents.emit(ScreenEvents.ShowSnackbarEvent(result.message))
                }
            }
        }
    }


    fun onLessonClick(lesson: Lesson) {
        viewModelScope.launch {
            _commonScreensEvents.emit(
                ScreenEvents.NavigationEvent(
                    route = Screen.DetailPage.route,
                    data = lesson
                )
            )
        }
    }

    fun uploadPractice() {
        if (_uploadState.value is UploadState.Uploading) return // prevent double upload

        viewModelScope.launch {
            if (!networkChecker.isInternetAvailable()) {
                _uploadState.value = UploadState.Error("No internet connection")
                return@launch
            }

            _uploadState.value = UploadState.Uploading(0)

            // Mock progress update
            for (i in 1..100 step 10) {
                delay(200)
                _uploadState.value = UploadState.Uploading(i)
            }

            val isSuccess = listOf(true, false).random()
            if (isSuccess) {
                _uploadState.value = UploadState.Success
            } else {
                _uploadState.value = UploadState.Error("Upload failed, please retry")
            }
        }
    }

    fun resetUploadState() {
        _uploadState.value = UploadState.Idle
    }

}


