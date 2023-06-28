package com.blackhawk.finance.data

import com.blackhawk.finance.model.Entry
import java.util.Date

object DataSource {

    val data = mutableListOf(
        Entry("0", 10.00, Date(), true)
    )

    init {
        repeat(10) {
            data.add(Entry(it.toString(), 10.0 * it, Date(), true))
        }
    }

}