package com.dev.lamariposa.boardgamesassociation.data.mapper

import com.dev.lamariposa.boardgamesassociation.data.db.entities.PersonEntity
import com.dev.lamariposa.boardgamesassociation.domain.model.Person

fun PersonEntity.toDomain(): Person {
    return Person(
        id = id,
        name = name,
        email = email,
        phone = phone,
        address = address
    )
}

fun Person.toEntity(): PersonEntity {
    return PersonEntity(
        id = id,
        name = name,
        email = email,
        phone = phone,
        address = address
    )
}
