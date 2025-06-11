package com.dev.lamariposa.boardgamesassociation.data.mapper

import android.util.Log
import com.dev.lamariposa.boardgamesassociation.domain.model.User
import com.google.firebase.auth.FirebaseUser

/**
 * Maps Firebase auth objects to domain objects
 */
object AuthMapper {
    
    /**
     * Maps a FirebaseUser to a domain User
     * @param firebaseUser The FirebaseUser to map
     * @return Mapped domain User or null if firebaseUser is null
     */
    fun mapToDomainUser(firebaseUser: FirebaseUser?): User? {
        if (firebaseUser == null) {
            Log.d("AuthMapper", "Firebase user is null, returning null domain user")
            return null
        }
        
        Log.d("AuthMapper", "Mapping Firebase user to domain user: ${firebaseUser.email}")
        return User(
            id = firebaseUser.uid,
            email = firebaseUser.email ?: "",
            displayName = firebaseUser.displayName,
            isEmailVerified = firebaseUser.isEmailVerified
        )
    }
}
