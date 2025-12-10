package com.example.studentapi.util

import com.example.activity_log_api.model.User
import com.example.activity_log_api.model.dto.UserRequest

object TestData {


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

    fun userRequest(
        username: String = "username",
        email: String = "email@email.com",
        password: String = "password",
    ) = UserRequest(
        username = username,
        email = email,
        password = password,
    )


}