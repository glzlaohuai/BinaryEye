package com.awesomeapp.xscaner.actions

import com.awesomeapp.xscaner.actions.mail.MailAction
import com.awesomeapp.xscaner.actions.mail.MatMsgAction
import com.awesomeapp.xscaner.actions.otpauth.OtpauthAction
import com.awesomeapp.xscaner.actions.search.OpenOrSearchAction
import com.awesomeapp.xscaner.actions.sms.SmsAction
import com.awesomeapp.xscaner.actions.tel.TelAction
import com.awesomeapp.xscaner.actions.vtype.vcard.VCardAction
import com.awesomeapp.xscaner.actions.vtype.vevent.VEventAction
import com.awesomeapp.xscaner.actions.web.WebAction
import com.awesomeapp.xscaner.actions.wifi.WifiAction

object ActionRegistry {
	val DEFAULT_ACTION: com.awesomeapp.xscaner.actions.IAction = OpenOrSearchAction

	private val REGISTRY: Set<com.awesomeapp.xscaner.actions.IAction> = setOf(
		MailAction,
		MatMsgAction,
		OtpauthAction,
		SmsAction,
		TelAction,
		VCardAction,
		VEventAction,
		WifiAction,
		// Try WebAction last because recognizing colloquial URLs is
		// very aggressive.
		WebAction
	)

	fun getAction(data: ByteArray): com.awesomeapp.xscaner.actions.IAction = com.awesomeapp.xscaner.actions.ActionRegistry.REGISTRY.find {
		it.canExecuteOn(data)
	} ?: com.awesomeapp.xscaner.actions.ActionRegistry.DEFAULT_ACTION
}
