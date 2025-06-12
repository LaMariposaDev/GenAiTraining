package com.dev.lamariposa.boardgamesassociation.domain.usecase

import android.util.Log
import com.dev.lamariposa.boardgamesassociation.domain.model.Person
import com.dev.lamariposa.boardgamesassociation.domain.repository.AuthRepository
import com.dev.lamariposa.boardgamesassociation.domain.repository.PersonRepository

/**
 * Use case to get users from Firebase and convert them to Person objects
 * for use in the app.
 */
class GetUsersFromFirebaseUseCase(
    private val authRepository: AuthRepository,
    private val personRepository: PersonRepository
) {
    suspend operator fun invoke(): List<Person> {
        Log.d("GetUsersFromFirebase", "Fetching all users from Firebase")
        try {
            // Get all users from Firebase
            val users = authRepository.getAllUsers()
            
            if (users.isNotEmpty()) {
                Log.d("GetUsersFromFirebase", "Found ${users.size} users in Firebase")
                
                val personList = mutableListOf<Person>()
                
                // Process each user and convert to Person
                users.forEach { user ->
                    // Check if this user already exists as a Person
                    val existingPerson = personRepository.getPersonByEmail(user.email)
                    
                    if (existingPerson != null) {
                        Log.d("GetUsersFromFirebase", "User already exists as Person: ${existingPerson.name}")
                        personList.add(existingPerson)
                    } else {
                        // Create a new Person from the User
                        val newPerson = Person(
                            name = user.email,
                            email = user.email
                        )
                        
                        // Save the new Person to the database
                        val personId = personRepository.addPerson(newPerson)
                        Log.d("GetUsersFromFirebase", "Created new Person with ID: $personId")
                        
                        personList.add(newPerson.copy(id = personId))
                    }
                }
                
                return personList
            } else {
                Log.w("GetUsersFromFirebase", "No users found in Firebase")
                return emptyList()
            }
        } catch (e: Exception) {
            Log.e("GetUsersFromFirebase", "Error fetching users", e)
            return emptyList()
        }
    }
}
