package com.awesomeapp.xscaner.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ap.android.trunk.sdk.ad.listener.APAdSplashListener
import com.ap.android.trunk.sdk.ad.splash.APAdSplash
import com.ap.android.trunk.sdk.core.others.APAdError
import com.awesomeapp.xscaner.ads.ID

class SplashActivity : AppCompatActivity(), APAdSplashListener {
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)

        // It's important _not_ to inflate a layout file here
        // because that would happen after the app is fully
        // initialized what is too late.

        val splash = APAdSplash(ID.splash_id, this)
        splash.load()
    }


    private fun gotoNext(): Unit {
        startActivity(Intent(applicationContext, CameraActivity::class.java))
        finish()
    }

    override fun onAPAdSplashLoadSuccess(p0: APAdSplash?) {
    }

    override fun onAPAdSplashLoadFail(p0: APAdSplash?, p1: APAdError?) {
        gotoNext()
    }

    override fun onAPAdSplashPresentSuccess(p0: APAdSplash?) {
    }

    override fun onAPAdSplashPresentFail(p0: APAdSplash?, p1: APAdError?) {
        gotoNext()
    }

    override fun onAPAdSplashClick(p0: APAdSplash?) {
    }

    override fun onAPAdSplashDidPresentLanding(p0: APAdSplash?) {
    }

    override fun onAPAdSplashDidDismissLanding(p0: APAdSplash?) {

    }

    override fun onAPAdSplashApplicationWillEnterBackground(p0: APAdSplash?) {
    }

    override fun onAPAdSplashDismiss(p0: APAdSplash?) {
        gotoNext()
    }

    override fun onAPAdSplashPresentTimeLeft(p0: Long) {
    }

    override fun onAPAdSplashRenderSuccess(p0: APAdSplash?) {
    }

    override fun onAPAdSplashDidAssembleViewFail(p0: APAdSplash?, p1: APAdError?) {
    }
}
