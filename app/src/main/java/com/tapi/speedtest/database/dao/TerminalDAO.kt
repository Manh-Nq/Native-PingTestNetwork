package com.tapi.speedtest.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tapi.speedtest.`object`.IP
import com.tapi.speedtest.database.entity.NetworkTrafficResult

@Dao
interface TerminalDAO {


    @Query("select * from ICMPTable")
    suspend fun getAllICMP(): List<NetworkTrafficResult>


    @Query("select * from ICMPTable where host = :host and destination =:dest")
    suspend fun getOrNull(host: IP, dest: IP): NetworkTrafficResult

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveResult(icmpEntity: NetworkTrafficResult)

    /*@Delete
    suspend fun deleteICMP(icmpEntity: NetworkTrafficResult)

    @Query("delete from ICMPTable")
    suspend fun deleteAllTable()

    @Update
    suspend fun updateICMP(icmpEntity: NetworkTrafficResult)*/


}