package com.hp.kotlinmvc.user

import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    fun saveUser(userDTO: NewUserDTO): User{
        var modelMapper = ModelMapper()
        return userRepository.save(modelMapper.map(userDTO, User::class.java))
    }

    fun updateUser(user: User): Unit{

    }

    fun getAllUsers(): List<User>{
        return userRepository.findAll().toList()
    }

    fun deleteUser(userId: Long){
        userRepository.deleteById(userId)
    }


}