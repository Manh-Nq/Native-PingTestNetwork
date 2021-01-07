package com.tapi.speedtest.database.terminaldb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tapi.speedtest.database.entity.ICMPEntity
import com.tapi.speedtest.database.dao.TerminalDAO

@Database(entities = [ICMPEntity::class], version = 1)
abstract class TerminalDB : RoomDatabase() {
    abstract val terminalDAO: TerminalDAO
}