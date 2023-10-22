package com.example.gymmate.fake_repository

import com.example.gymmate.data.userdata.UserEntity
import com.example.gymmate.data.userdata.UserEntityRepository
import kotlinx.coroutines.flow.Flow

class FakeUserEntityRepository: UserEntityRepository {
    override fun getUserByEmail(email: String): Flow<UserEntity> {
        TODO("Not yet implemented")
    }

    override fun deleteUserById(id: Int) {
        TODO("Not yet implemented")
    }

    override fun deleteUserByEmail(email: String) {
        TODO("Not yet implemented")
    }

    override fun deleteUserByEmailAndId(email: String, id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun insertUser(user: UserEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(user: UserEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: UserEntity) {
        TODO("Not yet implemented")
    }
}