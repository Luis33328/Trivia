package com.example.quiz.room

import androidx.room.*


@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): List<User>


    /*@Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>*/

    /*@Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User*/

    @Insert
    fun insertAll(vararg users: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(users: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user")
    fun nukeTable()
}