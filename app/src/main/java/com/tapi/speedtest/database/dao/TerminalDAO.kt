package com.tapi.speedtest.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tapi.speedtest.`object`.Constance
import com.tapi.speedtest.database.entity.NetworkTrafficEntity

@Dao
interface TerminalDAO {

    @Query("select * from ICMPTable")
    suspend fun getAllICMP(): List<NetworkTrafficEntity>


    @Query("select * from ICMPTable where host = :host and destination =:dest and (:time-validateThreshold) < ${Constance.TIME_CONFIG}")
    suspend fun getOrNull(host: String, dest: String, time: Long): NetworkTrafficEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveResult(icmpEntity: NetworkTrafficEntity)

    @Query("delete from ICMPTable")
    suspend fun deleteAllTable()

    /*@Delete
    suspend fun deleteICMP(icmpEntity: NetworkTrafficResult)



    @Update
    suspend fun updateICMP(icmpEntity: NetworkTrafficResult)*/


}