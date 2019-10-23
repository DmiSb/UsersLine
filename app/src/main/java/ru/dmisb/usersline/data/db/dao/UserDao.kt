package ru.dmisb.usersline.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.dmisb.usersline.data.db.entity.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Query("select * from User")
    fun selectAll() : LiveData<List<User>>

    @Query("select * from User where id = :userId")
    fun selectUser(userId: String) : LiveData<User>
}