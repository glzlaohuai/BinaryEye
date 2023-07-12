package com.awesomeapp.xscaner.app

import android.app.Application
import com.awesomeapp.xscaner.database.Database
import com.awesomeapp.xscaner.preference.Preferences

val db = Database()
val prefs = Preferences()

class ScannerApplication : Application() {
	override fun onCreate() {
		super.onCreate()
		db.open(this)
		prefs.init(this)
	}
}
