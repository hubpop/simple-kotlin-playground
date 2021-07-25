package com.hp.kotlinmvc.user

import com.hp.kotlinmvc.user.exception.NotAdultException

data class NewUserDTO (

    val name: String,
    var age: Int
){
    init {
        if (age < 18) throw NotAdultException()
    }
}
