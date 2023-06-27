package com.dewatwc.githubuser.data.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.dewatwc.githubuser.data.DatUser

@Dao
interface UserDao {

    @Query("SELECT * FROM data_user ORDER BY username")
    fun getUsers(): LiveData<List<DatUser>>

    @Query("SELECT * FROM data_user WHERE username = :userName")
    fun getDetail(userName: String): LiveData<List<DatUser>>

    @Query("SELECT * FROM data_user ORDER BY username ASC")
    fun cursorUser(): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userFavorites: DatUser)

    @Delete
    suspend fun delete(userFavorite: DatUser)
}