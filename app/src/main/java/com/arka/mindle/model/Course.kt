package com.arka.mindle.model

data class Course(
    val id: String,
    val title: String,
    val creatorName: String,
    val creatorReputation: Double,
    val durationMinutes: Int,
    val format: CourseFormat,
    val learnerCount: Int,
    val category: CourseCategory,
    val isNew: Boolean = false,
    val isEdited: Boolean = false,
    val progressPercent: Int? = null,
)

enum class CourseFormat { ARTICLE, VIDEO }

enum class CourseCategory(val labelResFallback: String) {
    TECHNOLOGY("Teknologi"),
    LANGUAGE("Bahasa"),
    BUSINESS("Bisnis"),
    DESIGN("Desain"),
    SCIENCE("Sains"),
    GROWTH("Pengembangan Diri"),
}
