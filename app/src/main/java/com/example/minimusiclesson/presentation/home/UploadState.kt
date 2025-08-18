package com.example.minimusiclesson.presentation.home


sealed class UploadState {
    object Idle : UploadState()
    data class Uploading(val progress: Int) : UploadState()
    object Success : UploadState()
    data class Error(val message: String) : UploadState()
}