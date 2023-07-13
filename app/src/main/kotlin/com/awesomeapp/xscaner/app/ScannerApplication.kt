package com.awesomeapp.xscaner.app

import android.app.Application
import com.ap.android.trunk.sdk.core.APSDK
import com.ap.android.trunk.sdk.core.others.APAdError
import com.ap.android.trunk.sdk.core.utils.APSDKListener
import com.awesomeapp.xscaner.ads.ID
import com.awesomeapp.xscaner.database.Database
import com.awesomeapp.xscaner.preference.Preferences

val db = Database()
val prefs = Preferences()

class ScannerApplication : Application(), APSDKListener {
    override fun onCreate() {
        super.onCreate()
        db.open(this)
        prefs.init(this)

        APSDK.init(this, ID.APP_ID, this)
    }


    override fun onSDKInitializeSuccess() {
    }

    override fun onSDKInitializeFail(p0: APAdError?) {
    }


}
