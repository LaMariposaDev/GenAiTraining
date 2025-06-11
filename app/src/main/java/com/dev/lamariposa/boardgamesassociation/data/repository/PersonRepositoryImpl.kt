package com.dev.lamariposa.boardgamesassociation.data.repository

import com.dev.lamariposa.boardgamesassociation.data.db.PersonDao
import com.dev.lamariposa.boardgamesassociation.data.mapper.toDomain
import com.dev.lamariposa.boardgamesassociation.data.mapper.toEntity
import com.dev.lamariposa.boardgamesassociation.domain.model.Person
import com.dev.lamariposa.boardgamesassociation.domain.repository.PersonRepository

class PersonRepositoryImpl(
    private val personDao: PersonDao
) : PersonRepository {
    
    override suspend fun insertPerson(person: Person): Long {
        return personDao.insertPerson(person.toEntity())
    }

    override suspend fun updatePerson(person: Person) {
        personDao.updatePerson(person.toEntity())
    }

    override suspend fun getPersonById(personId: Long): Person? {
        return personDao.getPersonById(personId)?.toDomain()
    }

    override suspend fun getAllPersons(): List<Person> {
        return personDao.getAllPersons().map { it.toDomain() }
    }

    override suspend fun deletePerson(personId: Long) {
        personDao.deletePerson(personId)
    }
}
