package com.dev.lamariposa.boardgamesassociation.domain.repository

import com.dev.lamariposa.boardgamesassociation.domain.model.Person

interface PersonRepository {
    suspend fun addPerson(person: Person): Long
    
    suspend fun updatePerson(person: Person)
    
    suspend fun getPersonById(id: Long): Person?
    
    suspend fun getPersonByEmail(email: String): Person?
    
    suspend fun getAllPersons(): List<Person>
    
    suspend fun deletePerson(id: Long)
}
