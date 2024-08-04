package com.example.peer_pulse.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<post>)

    @Query("SELECT * FROM posts WHERE preferences IN (:userPreferences) ORDER BY timestamp DESC")
    fun getPosts(userPreferences: List<String>): PagingSource<Int, post>

    /*@Query("SELECT * FROM posts WHERE topicName = :topicId ORDER BY timestamp DESC")
    fun getPostsByTopic(topicId: String): PagingSource<Int, post>*/

    @Query("DELETE FROM posts")
    suspend fun clearPosts()
}