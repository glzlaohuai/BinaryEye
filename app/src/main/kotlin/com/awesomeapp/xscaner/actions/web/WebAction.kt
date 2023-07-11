package com.awesomeapp.xscaner.actions.web

import android.content.Context
import com.awesomeapp.xscaner.R
import com.awesomeapp.xscaner.actions.IAction
import com.awesomeapp.xscaner.content.openUrl

object WebAction : IAction {
	private val colloquialRegex =
		"^(http[s]*://)*[A-Za-z0-9]{3,}\\.[A-Za-z]{2,}[^ \t\r\n]*$".toRegex()

	override val iconResId: Int = R.drawable.ic_action_open
	override val titleResId: Int = R.string.open_url

	override fun canExecuteOn(data: ByteArray): Boolean {
		return String(data).trim().matches(colloquialRegex)
	}

	override suspend fun execute(context: Context, data: ByteArray) {
		var url = String(data).trim()
		if (!url.startsWith("http") && !url.startsWith("ftp")) {
			url = "http://${url}"
		}
		context.openUrl(url)
	}
}
