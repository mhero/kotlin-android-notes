package com.mac.domain.repository

import com.mac.domain.domainmodel.Result
import com.mac.domain.domainmodel.User
import kotlinx.coroutines.channels.SendChannel


interface IAuthRepository {


//    suspend fun setAuthStateListener(channel: SendChannel<Result<Exception, User?>>): Result<Exception, Unit>

    suspend fun getCurrentUser(): Result<Exception, User?>

    suspend fun signOutCurrentUser(): Result<Exception, Unit>

    suspend fun deleteCurrentUser(): Result<Exception, Boolean>

    suspend fun createGoogleUser(idToken: String): Result<Exception, Unit>

}