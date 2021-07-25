package com.hp.kotlinmvc.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @PostMapping
    fun addUser(@RequestBody userDTO: NewUserDTO): ResponseEntity<User>{
        var savedUser = userService.saveUser(userDTO)
        return ResponseEntity(savedUser,HttpStatus.CREATED)
    }

    @GetMapping
    fun getUsers(): ResponseEntity<List<User>>{
        return ResponseEntity(userService.getAllUsers(), HttpStatus.OK)
    }

    @DeleteMapping
    fun deleteUser(@RequestBody userId: String): ResponseEntity<HttpStatus>{
        userService.deleteUser(userId.toLong())
        return ResponseEntity(HttpStatus.OK)
    }


}