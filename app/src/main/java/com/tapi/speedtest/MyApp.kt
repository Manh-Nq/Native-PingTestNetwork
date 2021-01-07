package com.tapi.speedtest

import android.app.Application
import androidx.room.Room
import com.tapi.speedtest.database.terminaldb.TerminalDB

class MyApp : Application() {
    companion object {
        lateinit var instance: Application
        lateinit var terminalDB: TerminalDB
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        terminalDB = Room.databaseBuilder(this, TerminalDB::class.java, "ICMPTable").build()

    }
}