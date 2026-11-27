package com.arka.mindle.model

data class DiscussionSnippet(
    val authorName: String,
    val authorRole: String,
    val timestamp: String,
    val content: String,
    val type: String,
)

data class CoursePreviewData(
    val overview: String,
    val feedbackAverage: Double,
    val feedbackCount: Int,
    val feedbackDistribution: List<Int>,
    val discussionSnippets: List<DiscussionSnippet>,
)
