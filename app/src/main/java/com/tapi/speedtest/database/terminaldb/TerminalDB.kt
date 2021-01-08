package com.tapi.speedtest.database.terminaldb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tapi.speedtest.database.entity.NetworkTrafficEntity
import com.tapi.speedtest.database.dao.TerminalDAO

@Database(entities = [NetworkTrafficEntity::class], version = 1)
abstract class TerminalDB : RoomDatabase() {
    abstract val terminalDAO: TerminalDAO
}