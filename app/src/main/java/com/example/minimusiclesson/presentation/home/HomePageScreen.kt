package com.example.minimusiclesson.presentation.home


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.minimusiclesson.R
import com.example.minimusiclesson.data.model.Lesson
import com.example.minimusiclesson.presentation.common.CommonScreenEvents
import com.example.minimusiclesson.presentation.common.CommonUiStates
import com.example.minimusiclesson.presentation.common.LessonItem
import com.example.minimusiclesson.presentation.common.UploadBottomSheetContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageScreen(
    navController: NavHostController,
    viewModel: HomePageViewModel = hiltViewModel()
) {
    val uiState by viewModel.screenState.collectAsState()
    val uploadState by viewModel.uploadState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val sheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true, confirmValueChange = { false })
    var showSheet by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        viewModel.commonScreensEvents.collect { event ->
            when (event) {
                is CommonScreenEvents.ShowSnackbarEvent -> {
                    snackbarHostState.showSnackbar(event.message)
                }

                is CommonScreenEvents.NavigationEvent<*> -> {
                    event.data?.let { data ->
                        navController.currentBackStackEntry?.savedStateHandle?.set("lesson", data)
                    }
                    navController.navigate(event.route)
                }
            }
        }
    }



    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.shadow(4.dp),
                title = { Text(text = stringResource(id = R.string.lesson_list)) })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            // Screen UI
            when (uiState) {
                is CommonUiStates.InitialState -> Unit
                is CommonUiStates.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is CommonUiStates.Success -> {
                    val lessons = (uiState as CommonUiStates.Success<List<Lesson>>).data
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        itemsIndexed(lessons) { _, lesson ->
                            LessonItem(
                                lesson = lesson,
                                onLessonClick = { viewModel.onLessonClick(lesson) },
                                onSubmitPracticeClick = { showSheet = true }
                            )
                        }
                    }
                }

                is CommonUiStates.Error -> {
                    val message = (uiState as CommonUiStates.Error).message
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = message ?: "Unknown error")
                    }
                }
            }

            if (showSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showSheet = false
                    },
                    sheetState = sheetState
                ) {
                    UploadBottomSheetContent(
                        uploadState = uploadState,
                        onUploadClick = { viewModel.uploadPractice() },
                        onRetryClick = { viewModel.uploadPractice() },
                        onCloseClick = {
                            showSheet = false
                            viewModel.resetUploadState()
                        }
                    )
                }
            }
        }
    }
}

