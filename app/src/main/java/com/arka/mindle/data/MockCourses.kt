package com.arka.mindle.data

import com.arka.mindle.model.Course
import com.arka.mindle.model.CourseCategory
import com.arka.mindle.model.CourseFormat

object MockCourses {
    val categories = CourseCategory.entries.toList()

    val feed: List<Course> = listOf(
        Course(
            id = "c1",
            title = "Dasar-Dasar Python untuk Pemula",
            creatorName = "Rina Putri",
            creatorReputation = 4.8,
            durationMinutes = 45,
            format = CourseFormat.ARTICLE,
            learnerCount = 1240,
            category = CourseCategory.TECHNOLOGY,
            isNew = true,
        ),
        Course(
            id = "c2",
            title = "Menulis Email Profesional dalam Bahasa Inggris",
            creatorName = "Andi Pratama",
            creatorReputation = 4.6,
            durationMinutes = 30,
            format = CourseFormat.ARTICLE,
            learnerCount = 860,
            category = CourseCategory.LANGUAGE,
        ),
        Course(
            id = "c3",
            title = "Membangun Kebiasaan Belajar yang Bertahan",
            creatorName = "Sari Dewi",
            creatorReputation = 4.9,
            durationMinutes = 20,
            format = CourseFormat.ARTICLE,
            learnerCount = 2310,
            category = CourseCategory.GROWTH,
        ),
        Course(
            id = "c4",
            title = "Pengenalan UI Design: Prinsip Hierarki Visual",
            creatorName = "Bima Sakti",
            creatorReputation = 4.7,
            durationMinutes = 35,
            format = CourseFormat.VIDEO,
            learnerCount = 540,
            category = CourseCategory.DESIGN,
            isEdited = true,
        ),
        Course(
            id = "c5",
            title = "Dasar Keuangan Pribadi untuk Mahasiswa",
            creatorName = "Nadia Rahma",
            creatorReputation = 4.5,
            durationMinutes = 25,
            format = CourseFormat.ARTICLE,
            learnerCount = 1780,
            category = CourseCategory.BUSINESS,
        ),
        Course(
            id = "c6",
            title = "Cara Kerja Internet: Dari DNS sampai HTTP",
            creatorName = "Dimas Arief",
            creatorReputation = 4.8,
            durationMinutes = 40,
            format = CourseFormat.ARTICLE,
            learnerCount = 920,
            category = CourseCategory.TECHNOLOGY,
        ),
    )

    val continueLearning: List<Course> = listOf(
        Course(
            id = "c3",
            title = "Membangun Kebiasaan Belajar yang Bertahan",
            creatorName = "Sari Dewi",
            creatorReputation = 4.9,
            durationMinutes = 20,
            format = CourseFormat.ARTICLE,
            learnerCount = 2310,
            category = CourseCategory.GROWTH,
            progressPercent = 45,
        ),
    )
}
