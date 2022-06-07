package com.example.Davalebaa9


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface API {

    companion object {
        fun create(): API = Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }

    @GET("users")
    fun getUsers(): retrofit2.Call<UserList>

    @GET("users/{id}")
    fun getUserById(@Path("id") id: Int): retrofit2.Call<UserResponse>

    @POST("users")
    fun createUser(@Body request: CreateUserRequest): retrofit2.Call<CreateUserResponse>
}

annotation class Body

annotation class Path(val value: String)

annotation class POST(val value: String)

annotation class GET(val value: String)

class GsonConverterFactory {

}

class Retrofit {

}
