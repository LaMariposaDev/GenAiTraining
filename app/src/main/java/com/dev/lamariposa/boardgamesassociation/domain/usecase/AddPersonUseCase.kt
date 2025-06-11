package com.dev.lamariposa.boardgamesassociation.domain.usecase

import com.dev.lamariposa.boardgamesassociation.domain.model.Person
import com.dev.lamariposa.boardgamesassociation.domain.repository.PersonRepository

class AddPersonUseCase(private val repository: PersonRepository) {
    suspend operator fun invoke(person: Person): Long {
        return repository.insertPerson(person)
    }
}
