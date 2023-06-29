package com.blackhawk.finance

import android.app.Application
import com.blackhawk.finance.database.AppDatabase

class FinanceApplication : Application() {

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

}