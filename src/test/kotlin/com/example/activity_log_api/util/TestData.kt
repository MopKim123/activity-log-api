package com.example.studentapi.util

import com.example.activity_log_api.model.User

object TestData {

    //Course
//    fun courseData(id: Long = 0, name: String = "BSIT") = Course( id = id, name = name )
//    fun courseRequestDtoData( name: String = "BSIT") = CourseRequestDto( name = name )

    //User
    fun userData(
        id: Long = 0,
        username: String = "username",
        email: String = "email@email.com",
        password: String = "password",
    ): User = User(
        id = id,
        username = username,
        email = email,
        password = password,
    )
}