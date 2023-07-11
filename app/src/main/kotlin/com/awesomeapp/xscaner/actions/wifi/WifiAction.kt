package com.awesomeapp.xscaner.actions.wifi

import android.content.Context
import com.awesomeapp.xscaner.R
import com.awesomeapp.xscaner.actions.IAction
import com.awesomeapp.xscaner.widget.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object WifiAction : IAction {
	override val iconResId = R.drawable.ic_action_wifi
	override val titleResId = R.string.connect_to_wifi

	var password: String? = null

	override fun canExecuteOn(data: ByteArray): Boolean =
		WifiConnector.parse(String(data)) {
			password = it
		} != null

	override suspend fun execute(
		context: Context,
		data: ByteArray
	) = withContext(Dispatchers.IO) {
		val wifiConfig = WifiConnector.parse(
			String(data)
		) ?: return@withContext
		val message = WifiConnector.addNetwork(context, wifiConfig)
		withContext(Dispatchers.Main) {
			context.toast(message)
		}
	}
}
