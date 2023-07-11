package com.awesomeapp.xscaner.actions.otpauth

import com.awesomeapp.xscaner.R
import com.awesomeapp.xscaner.actions.SchemeAction

object OtpauthAction : SchemeAction() {
	override val iconResId: Int = R.drawable.ic_action_otpauth
	override val titleResId: Int = R.string.otpauth_add
	override val scheme: String = "otpauth"
}
