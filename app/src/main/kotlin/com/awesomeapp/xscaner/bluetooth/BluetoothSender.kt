package com.awesomeapp.xscaner.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.support.v7.preference.ListPreference
import com.awesomeapp.xscaner.database.Scan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.OutputStreamWriter
import java.util.*

fun Scan.sendBluetoothAsync(
	host: String,
	callback: (Boolean, Boolean) -> Unit
) {
	CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
		val connected = if (isConnected) {
			true
		} else {
			connect(host)
		}
		val sent = if (connected) {
			send(content)
		} else {
			false
		}
		withContext(Dispatchers.Main) {
			callback(connected, sent)
		}
	}
}

fun setBluetoothHosts(listPref: ListPreference) {
	val devices = try {
		BluetoothAdapter.getDefaultAdapter().bondedDevices
	} catch (e: SecurityException) {
		// Do nothing, either the user has denied Bluetooth access
		// or the permission was removed by the system. We're catching
		// the exception to keep the app from crashing.
		null
	} ?: return
	listPref.entries = devices.map {
		it.name
	}.toTypedArray()
	listPref.entryValues = devices.map {
		it.address
	}.toTypedArray()
	listPref.callChangeListener(listPref.value)
}

private var socket: BluetoothSocket? = null
private var writer: OutputStreamWriter? = null

private val blue = BluetoothAdapter.getDefaultAdapter()
private val uuid = UUID.fromString(
	"8a8478c9-2ca8-404b-a0de-101f34ab71ae"
)

private var isConnected = false

private fun connect(deviceName: String): Boolean = try {
	val device = findByName(deviceName) ?: throw RuntimeException(
		"Bluetooth device not found"
	)
	socket = device.createRfcommSocketToServiceRecord(uuid)
	socket?.connect()
	writer = socket?.outputStream?.writer()
	isConnected = true
	true
} catch (e: Exception) {
	close()
	false
}

private fun send(message: String): Boolean = try {
	writer?.apply {
		write(message)
		write("\n")
		flush()
	}
	true
} catch (e: Exception) {
	close()
	false
}

private fun close() {
	try {
		writer?.close()
	} catch (e: IOException) {
		// Catch exception if writer wasn't initialized.
	}
	try {
		socket?.close()
	} catch (e: IOException) {
		// Nothing we can do about this but keep
		// the app from crashing.
	}
	writer = null
	socket = null
	isConnected = false
}

private fun findByName(findableName: String): BluetoothDevice? {
	val deviceList = blue.bondedDevices
	for (device in deviceList) {
		if (device.address == findableName) {
			return device
		}
	}
	return null
}
