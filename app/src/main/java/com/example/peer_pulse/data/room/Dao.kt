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
    @Query("SELECT * FROM posts WHERE preferences IN (:userPreferences) AND timestamp>:lastweek ORDER BY timestamp DESC ")
    fun getPostbyLastweek(userPreferences: List<String>, lastweek: Long):PagingSource<Int,post>

    @Query("SELECT * FROM posts WHERE preferences IN (:userPreferences) AND timestamp>:lastmonth ORDER BY timestamp DESC ")
    fun getPostbyLastmonth(userPreferences: List<String>, lastmonth: Long):PagingSource<Int,post>

    @Query("SELECT * FROM posts WHERE preferences IN (:userPreferences) AND timestamp>:lastyear ORDER BY timestamp DESC ")
    fun getPostbyLastyear(userPreferences: List<String>, lastyear: Long):PagingSource<Int,post>

    @Query("DELETE FROM posts")
    suspend fun clearPosts()
}