package com.dev.lamariposa.boardgamesassociation.domain.repository

import com.dev.lamariposa.boardgamesassociation.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    suspend fun insertPerson(person: Person): Long
    suspend fun updatePerson(person: Person)
    suspend fun getPersonById(personId: Long): Person?
    suspend fun getAllPersons(): List<Person>
    suspend fun deletePerson(personId: Long)
}
