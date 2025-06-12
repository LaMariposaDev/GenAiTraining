package com.dev.lamariposa.boardgamesassociation.data.repository

import android.util.Log
import com.dev.lamariposa.boardgamesassociation.data.db.PersonDao
import com.dev.lamariposa.boardgamesassociation.data.mapper.toDomain
import com.dev.lamariposa.boardgamesassociation.data.mapper.toEntity
import com.dev.lamariposa.boardgamesassociation.domain.model.Person
import com.dev.lamariposa.boardgamesassociation.domain.repository.PersonRepository

class PersonRepositoryImpl(
    private val personDao: PersonDao
) : PersonRepository {
    override suspend fun addPerson(person: Person): Long {
        Log.d("PersonRepository", "Adding person: ${person.name}")
        return personDao.insertPerson(person.toEntity())
    }

    override suspend fun updatePerson(person: Person) {
        Log.d("PersonRepository", "Updating person: ${person.name}")
        personDao.updatePerson(person.toEntity())
    }

    override suspend fun getPersonById(id: Long): Person? {
        Log.d("PersonRepository", "Getting person by ID: $id")
        return personDao.getPersonById(id)?.toDomain()
    }
    
    override suspend fun getPersonByEmail(email: String): Person? {
        Log.d("PersonRepository", "Getting person by email: $email")
        return personDao.getPersonByEmail(email)?.toDomain()
    }

    override suspend fun getAllPersons(): List<Person> {
        Log.d("PersonRepository", "Getting all persons")
        return personDao.getAllPersons().map { it.toDomain() }
    }

    override suspend fun deletePerson(id: Long) {
        Log.d("PersonRepository", "Deleting person with ID: $id")
        personDao.deletePerson(id)
    }
}
