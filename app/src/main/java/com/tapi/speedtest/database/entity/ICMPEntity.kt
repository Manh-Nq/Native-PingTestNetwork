package com.tapi.speedtest.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ICMPTable")
class ICMPEntity {

    @PrimaryKey
    @ColumnInfo(name = "destination")
    var destination: String = ""

    @ColumnInfo(name = "host")
    var host: String = ""


    @ColumnInfo(name = "timeAverage")
    var timeAverage: Int = 0

    @ColumnInfo(name = "timeScanned")
    var timeScanned: Long = 0L

    override fun toString(): String {
        return "destination : $destination  host : $host  timeAverage: $timeAverage ms timeScanned :$timeScanned ms "
    }
}