package com.awesomeapp.xscaner.view

import android.content.Context
import android.media.AudioManager
import com.awesomeapp.xscaner.app.prefs
import com.awesomeapp.xscaner.media.beepConfirm
import com.awesomeapp.xscaner.media.beepError
import com.awesomeapp.xscaner.os.error
import com.awesomeapp.xscaner.os.getVibrator
import com.awesomeapp.xscaner.os.vibrate

fun Context.scanFeedback() {
	if (prefs.vibrate) {
		getVibrator().vibrate()
	}
	if (prefs.beep && !isSilent()) {
		beepConfirm()
	}
}

fun Context.errorFeedback() {
	if (prefs.vibrate) {
		getVibrator().error()
	}
	if (prefs.beep && !isSilent()) {
		beepError()
	}
}

private fun Context.isSilent(): Boolean {
	val am = getSystemService(Context.AUDIO_SERVICE) as AudioManager
	return when (am.ringerMode) {
		AudioManager.RINGER_MODE_SILENT,
		AudioManager.RINGER_MODE_VIBRATE -> true
		else -> false
	}
}
