package com.dev.lamariposa.boardgamesassociation.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dev.lamariposa.boardgamesassociation.data.db.entities.BoardGameEntity
import com.dev.lamariposa.boardgamesassociation.data.db.entities.MyBoardGameEntity
import com.dev.lamariposa.boardgamesassociation.data.db.entities.PersonEntity

@Database(
    entities = [
        BoardGameEntity::class,
        PersonEntity::class,
        MyBoardGameEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun boardGameDao(): BoardGameDao
    abstract fun personDao(): PersonDao
    abstract fun myBoardGameDao(): MyBoardGameDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        operator fun invoke(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "board_games_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
