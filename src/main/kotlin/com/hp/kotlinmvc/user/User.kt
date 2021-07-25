package com.hp.kotlinmvc.user

import com.hp.kotlinmvc.user.exception.NotAdultException
import javax.persistence.*

@Entity
@Table
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    var age: Int
){
    init {
        if (age < 18) throw NotAdultException()
    }
}