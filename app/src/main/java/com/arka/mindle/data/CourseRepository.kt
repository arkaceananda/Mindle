package com.arka.mindle.data

import com.arka.mindle.model.Course
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface CourseRepository {
    fun observeFeed(): Flow<List<Course>>
    fun observeContinueLearning(): Flow<List<Course>>
}

class MockCourseRepository : CourseRepository {
    override fun observeFeed(): Flow<List<Course>> = flowOf(MockCourses.feed)

    override fun observeContinueLearning(): Flow<List<Course>> = flowOf(MockCourses.continueLearning)
}
