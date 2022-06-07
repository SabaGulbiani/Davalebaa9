package com.example.Davalebaa9
import androidx.room.Entity
import androidx.room.PrimaryKey

data class UserList(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<User>
)

data class UserResponse(
    val data: User
)

data class CreateUserRequest(
    val name: String,
    val job: String
)

data class CreateUserResponse(
    val id: Int,
    val name: String,
    val job: String,
    val createdAt: String
)

data class User(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String?
) {
    fun toEntity(): UserEntity {
        return UserEntity(id, email, first_name, last_name, avatar)
    }
}

@Entity
data class UserEntity(
    @PrimaryKey
    var id: Int = -1,
    var email: String = "",
    var first_name: String = "",
    var last_name: String = "",
    var avatar: String? = null
) {
    fun toModel(): User {
        return User(id, email, first_name, last_name, avatar)
    }
}
