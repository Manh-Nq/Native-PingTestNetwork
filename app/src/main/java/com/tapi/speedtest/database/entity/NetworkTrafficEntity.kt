package com.tapi.speedtest.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ICMPTable")
class NetworkTrafficEntity {

    @PrimaryKey
    @ColumnInfo(name = "destination")
    var destination: String = ""

    @ColumnInfo(name = "host")
    var host: String = ""


    @ColumnInfo(name = "duration")
    var duration: Int = 0

    @ColumnInfo(name = "validateThreshold")
    var validateThreshold: Long = 0L

    override fun toString(): String {
        return "destination : $destination  host : $host  duration: $duration ms validateThreshold :$validateThreshold ms "
    }
}