package com.example.data.source.remote

import android.util.Log
import com.example.data.model.request.LoginRequest
import com.example.data.model.request.SignupRequest
import com.example.data.model.request.VerifyCodeRequest
import com.example.data.model.response.AuthDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthFirebaseDataSourceImpl(private val profileFirebaseDataSourceImpl: ProfileFirebaseDataSourceImpl) :
    AuthRemoteDataSource {

    private val firebaseAuth: FirebaseAuth = Firebase.auth

    // region Getters for current Firebase state
    fun getCurrentFirebaseUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun getCurrentFirebaseUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }
    //endregion

    //region Firebase Authentication Methods
    private suspend fun signUpWithEmailPassword(
        email: String, name: String, mobile: String, location: String, password: String
    ): FirebaseUser {
        try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            authResult.user?.let {
                it.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())
                profileFirebaseDataSourceImpl.createUser(
                    firebaseUser = it,
                    mobile = mobile,
                    location = location
                )
                return it
            } ?: throw Exception("Firebase user was null after sign up.")
        } catch (e: Exception) {
            Log.e("FirebaseAuthSource", "signUpWithEmailPassword failed", e)
            throw e
        }
    }


    private suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            authResult.user ?: throw Exception("Firebase user was null after sign in.")
        } catch (e: Exception) {
            Log.e("FirebaseAuthSource", "signInWithEmailPassword failed", e)
            throw e
        }
    }


    fun signOut() {
        firebaseAuth.signOut()
    }
    //endregion


    private suspend fun FirebaseUser.toAuthDto(): AuthDto {
        return AuthDto(
            image = photoUrl.toString(),
            name = displayName,
            token = getIdToken(false).await().token,
            uuid = uid
        )
    }

    override suspend fun signup(body: SignupRequest): AuthDto {
        return signUpWithEmailPassword(
            email = body.email,
            password = body.password,
            name = body.name,
            mobile = body.mobile,
            location = body.location
        ).toAuthDto()
    }

    override suspend fun login(body: LoginRequest): AuthDto {
        return signInWithEmailPassword(
            email = body.email, password = body.password
        ).toAuthDto()
    }

    override suspend fun verifyCode(body: VerifyCodeRequest): AuthDto {
        TODO("Not yet implemented")
    }

    // TODO: Add other Firebase Auth methods as needed:
    // - Send password reset email
    // - Link accounts
    // - Update profile (displayName, photoUrl on FirebaseUser)
    // - Get ID token (firebaseAuth.currentUser?.getIdToken(forceRefresh: Boolean)?.await()?.token)
}