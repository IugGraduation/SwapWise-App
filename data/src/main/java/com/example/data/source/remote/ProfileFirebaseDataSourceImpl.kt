package com.example.data.source.remote

import android.util.Log
import com.example.data.model.request.ResetPasswordRequest
import com.example.data.model.response.profile.ProfileItemDto
import com.example.data.model.response.profile.ProfilePostItemDto
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.net.URI
import java.util.Date


class ProfileFirebaseDataSourceImpl : ProfileRemoteDataSource {

    private val db: FirebaseFirestore = Firebase.firestore
    private val usersCollection = db.collection("users")


    /**
     * Creates a user profile document in FireStore after successful Firebase Auth signup.
     * The document ID will be the FirebaseUser's UID.
     */
    suspend fun createUser(
        firebaseUser: FirebaseUser,
        mobile: String? = null,
        location: String? = null,
    ) {
        try {
            val userProfile = ProfileItemDto(
                uuid = firebaseUser.uid,
                email = firebaseUser.email,
                name = firebaseUser.displayName,
                image = firebaseUser.photoUrl?.toString(),
                mobile = mobile ?: firebaseUser.phoneNumber,
                //todo: add location as 2 inputs, state (from a list) and detailed location
                place = location,
                // createdAt will be set by @ServerTimestamp
                lastLoginAt = Date()
            )
            usersCollection.document(firebaseUser.uid).set(userProfile).await()
            Log.d("TAG", "User profile created for UID: ${firebaseUser.uid}")
        } catch (e: Exception) {
            Log.e("TAG", "Error creating user profile for UID: ${firebaseUser.uid}", e)
            throw e
        }
    }


    suspend override fun getCurrentUserDataById(id: String): ProfileItemDto? {
        return try {
            val documentSnapshot = usersCollection.document(id).get().await()
            val profileDto = documentSnapshot.toObject<ProfileItemDto>()

            Log.d("TAG", "User profile fetched for UID: $id \n User: $profileDto")
            profileDto
        } catch (e: Exception) {
            Log.e("TAG", "Error fetching user profile for UID: $id", e)
            throw e
        }
    }

    /**
     * Updates specific fields in a user's profile in FireStore.
     * Uses SetOptions.merge() to only update provided fields and not overwrite the whole document.
     */
    //todo: review and implement this update function/update firebaseUser as well
    private suspend fun updateUserInfo(uid: String, updates: Map<String, Any?>): Boolean {
        if (uid.isBlank()) throw IllegalArgumentException("UID cannot be blank")
        try {
            usersCollection.document(uid).set(updates, SetOptions.merge()).await()
            Log.d("TAG", "User profile fields updated for UID: $uid")
            return true
        } catch (e: Exception) {
            Log.e("TAG", "Error updating user profile fields for UID: $uid", e)
            throw e
        }
    }


    suspend fun updateUserLastLogin(uid: String): Boolean {
        return updateUserInfo(uid, mapOf("lastLoginAt" to Date()))
    }

    override suspend fun getCurrentUserPosts(): List<ProfilePostItemDto> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserInfo(
        image: URI?,
        name: String,
        mobile: String,
        bio: String,
        place: String
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun resetPassword(request: ResetPasswordRequest) {
        TODO("Not yet implemented")
    }

}