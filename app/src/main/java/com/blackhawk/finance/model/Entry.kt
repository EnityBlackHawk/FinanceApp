package com.blackhawk.finance.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Entry(

    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var tag: String,
    var value: Double,
    var date: Long,
    var isCredit: Boolean
    ) {}