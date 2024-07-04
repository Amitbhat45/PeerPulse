package com.example.peer_pulse.data.room

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [post::class], version = 2)
@TypeConverters(com.example.peer_pulse.data.room.Converters::class)
abstract class PostsDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        private var INSTANCE: PostsDatabase? = null

        fun getDatabase(context: Context): PostsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostsDatabase::class.java,
                    "posts_database"
                ).addMigrations(MIGRATION_1_2).build()
                INSTANCE = instance
                instance
            }
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Handle the data type change
                database.execSQL("ALTER TABLE posts RENAME TO posts_temp")
                database.execSQL("""
                    CREATE TABLE posts (
                        id TEXT NOT NULL PRIMARY KEY,
                        userId TEXT NOT NULL,
                        title TEXT NOT NULL,
                        description TEXT NOT NULL,
                        imageUrl TEXT NOT NULL,
                        timestamp INTEGER NOT NULL,
                        likes INTEGER NOT NULL,
                        preferences TEXT NOT NULL,
                        preferenceId TEXT NOT NULL
                    )
                """)
                database.execSQL("""
                    INSERT INTO posts (id, userId, title, description, imageUrl, timestamp, likes, preferences, preferenceId)
                    SELECT id, userId, title, description, imageUrl, CAST(timestamp AS INTEGER), likes, preferences, preferenceId
                    FROM posts_temp
                """)
                database.execSQL("DROP TABLE posts_temp")
            }
        }
    }
}
