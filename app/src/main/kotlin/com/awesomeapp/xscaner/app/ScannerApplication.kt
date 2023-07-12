package com.awesomeapp.xscaner.app

import android.app.Application
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.ap.android.trunk.sdk.core.APSDK
import com.ap.android.trunk.sdk.core.others.APAdError
import com.ap.android.trunk.sdk.core.utils.APSDKListener
import com.ap.android.trunk.sdk.debug.activity.APADDebugActivity
import com.awesomeapp.xscaner.database.Database
import com.awesomeapp.xscaner.preference.Preferences

val db = Database()
val prefs = Preferences()

class ScannerApplication : Application(), APSDKListener {
    override fun onCreate() {
        super.onCreate()
        db.open(this)
        prefs.init(this)

        APSDK.init(this, "44901c5e-1e78-4103-9c89-a179b3ba254d", this)

        Handler(Looper.getMainLooper()).postDelayed({
            var intent = Intent(this@ScannerApplication, APADDebugActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            this@ScannerApplication.startActivity(intent)
        }, 5000)
    }


    override fun onSDKInitializeSuccess() {
    }

    override fun onSDKInitializeFail(p0: APAdError?) {
    }


}
