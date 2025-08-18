package com.example.minimusiclesson.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Lesson(
    @SerializedName("mentor_name")
    val mentorName: String,

    @SerializedName("lesson_title")
    val lessonTitle: String,

    @SerializedName("video_thumbnail_url")
    val videoThumbnailUrl: String,

    @SerializedName("lesson_image_url")
    val lessonImageUrl: String,

    @SerializedName("video_url")
    val videoUrl: String
) : Parcelable