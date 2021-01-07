package com.tapi.speedtest.database.dao

import androidx.room.*
import com.tapi.speedtest.database.entity.ICMPEntity

@Dao
interface TerminalDAO {


    @Query("select * from ICMPTable")
    suspend fun getAllICMP(): List<ICMPEntity>


    @Query("select * from ICMPTable where host = :ip and destination =:dest")
    suspend fun getUserByID(ip: String, dest: String): ICMPEntity

    @Insert
    suspend fun saveICMP(icmpEntity: ICMPEntity)

    @Delete
    suspend fun deleteICMP(icmpEntity: ICMPEntity)

    @Query("delete from ICMPTable")
    suspend fun deleteAllTable()

    @Update
    suspend fun updateICMP(icmpEntity: ICMPEntity)


}