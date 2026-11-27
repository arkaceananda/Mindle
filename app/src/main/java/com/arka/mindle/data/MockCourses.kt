package com.arka.mindle.data

import com.arka.mindle.model.Course
import com.arka.mindle.model.CourseCategory
import com.arka.mindle.model.CourseFormat
import com.arka.mindle.model.CoursePreviewData
import com.arka.mindle.model.DiscussionSnippet

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

    val previewData: Map<String, CoursePreviewData> = mapOf(
        "c1" to CoursePreviewData(
            overview = "Pelajari dasar-dasar Python dari nol: variabel, tipe data, percabangan, perulangan, fungsi, hingga struktur data dasar. Cocok untuk pemula yang belum pernah coding sebelumnya.",
            feedbackAverage = 4.8,
            feedbackCount = 312,
            feedbackDistribution = listOf(248, 42, 12, 6, 4),
            discussionSnippets = listOf(
                DiscussionSnippet("Rina Putri", "CREATOR", "2 hari lalu", "Terima kasih sudah belajar! Kalau ada pertanyaan, silakan tanya di sini.", "COMMENT"),
                DiscussionSnippet("Budi Santoso", "VOLUNTEER", "5 hari lalu", "Untuk pemula, bagian perulangan paling menantang tapi paling seru.", "COMMENT"),
            ),
        ),
        "c2" to CoursePreviewData(
            overview = "Kuasai cara menulis email profesional dalam bahasa Inggris: struktur, kosakata, tone, dan contoh nyata untuk berbagai situasi kerja.",
            feedbackAverage = 4.6,
            feedbackCount = 198,
            feedbackDistribution = listOf(140, 38, 12, 5, 3),
            discussionSnippets = listOf(
                DiscussionSnippet("Andi Pratama", "CREATOR", "1 minggu lalu", "Jangan lupa cek bagian formal vs informal di unit 3.", "COMMENT"),
            ),
        ),
    )
}
