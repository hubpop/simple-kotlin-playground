package com.hp.kotlinmvc.user

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.util.Assert
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootTest
@EnableWebMvc
@AutoConfigureMockMvc
@ActiveProfiles("test")
internal class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private lateinit var gson: Gson

    private lateinit var user: User
    private lateinit var newUserDTO: NewUserDTO

    @BeforeEach
    fun init(){
        gson = Gson()

        user = User(1, "User 1 name", 18)
        newUserDTO = NewUserDTO("User 1 name", 18)
    }

    @Test
    @SqlGroup(
            Sql(value = ["classpath:database/delete_all.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    fun addUser() {
        //given
        var jsonContent = gson.toJson(newUserDTO)

        //when then
        var post = MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)

        mockMvc.perform(post)
                .andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test

    @SqlGroup(
        Sql(value = ["classpath:database/insert_users.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(value = ["classpath:database/delete_all.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    fun getUsers() {
        //given
        val tokenType: TypeToken<List<User>> = object : TypeToken<List<User>>() {}

        //when
        var get = MockMvcRequestBuilders
                .get("/user")

        var result = mockMvc.perform(get)
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        //then
        var contentAsString = result.response.contentAsString

        var responseUserList: List<User>
        responseUserList = gson.fromJson(contentAsString, tokenType.type)

        responseUserList.forEach{
            print(it)
        }

        Assert.isTrue(responseUserList.contains(user))

    }

    @Test
    @SqlGroup(
            Sql(value = ["classpath:database/insert_users.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            Sql(value = ["classpath:database/delete_all.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    fun deleteUser() {
        //given
        val tokenType: TypeToken<List<User>> = object : TypeToken<List<User>>() {}

        //when and then
        var delete = MockMvcRequestBuilders
                .delete("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("1")

        mockMvc.perform(delete)
                .andExpect(MockMvcResultMatchers.status().isOk)
    }
}