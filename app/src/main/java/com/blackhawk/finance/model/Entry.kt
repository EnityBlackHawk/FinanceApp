package com.blackhawk.finance.model

import java.util.Date

data class Entry(
    var tag: String,
    var value: Double,
    var date: Date,
    var isCredit: Boolean
    ) {}