package com.dev.lamariposa.boardgamesassociation.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dev.lamariposa.boardgamesassociation.data.db.entities.PersonEntity

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: PersonEntity): Long

    @Update
    suspend fun updatePerson(person: PersonEntity)

    @Query("SELECT * FROM person WHERE id = :personId")
    suspend fun getPersonById(personId: Long): PersonEntity?

    @Query("SELECT * FROM person ORDER BY name ASC")
    suspend fun getAllPersons(): List<PersonEntity>

    @Query("DELETE FROM person WHERE id = :personId")
    suspend fun deletePerson(personId: Long)
}
