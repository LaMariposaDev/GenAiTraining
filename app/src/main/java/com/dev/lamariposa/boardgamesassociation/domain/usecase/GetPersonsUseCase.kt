package com.dev.lamariposa.boardgamesassociation.domain.usecase

import com.dev.lamariposa.boardgamesassociation.domain.repository.PersonRepository

class GetPersonsUseCase(private val repository: PersonRepository) {
    suspend operator fun invoke() = repository.getAllPersons()
}
