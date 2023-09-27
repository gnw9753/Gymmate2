package com.example.gymmate

import com.example.gymmate.data.UserModel
import org.junit.Assert
import org.junit.Test

class UserModelTest {

    @Test
    fun userModel_isCorrect() {
        var userModel=UserModel(1,"test@test.com","test","male",20,180,90,"goals","20")
        Assert.assertEquals(1, userModel.id)
        Assert.assertEquals("test@test.com", userModel.email)
    }

}