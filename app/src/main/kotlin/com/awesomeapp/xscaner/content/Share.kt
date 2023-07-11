package com.awesomeapp.xscaner.content

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import com.awesomeapp.xscaner.BuildConfig
import com.awesomeapp.xscaner.R
import com.awesomeapp.xscaner.widget.toast
import java.io.File

fun Context.execShareIntent(intent: Intent): Boolean = if (
    !startIntent(intent)
) {
    toast(R.string.cannot_resolve_action)
    false
} else {
    true
}

fun Context.startIntent(intent: Intent): Boolean = try {
    // Avoid using `intent.resolveActivity()` at API level 30+ due
    // to the new package visibility restrictions. In order for
    // `resolveActivity()` to "see" another package, we would need
    // to list that package/intent in a `<queries>` block in the
    // Manifest. But since we used `resolveActivity()` only to avoid
    // an exception if the Intent cannot be resolved, it's much easier
    // and more robust to just try and catch that exception if
    // necessary.
    startActivity(intent)
    true
} catch (e: ActivityNotFoundException) {
    false
}

fun Context.openUrl(url: String, silent: Boolean = false): Boolean {
    val intent = Intent(Intent.ACTION_VIEW, url.parseAndNormalizeUri())
    return if (silent) {
        startIntent(intent)
    } else {
        execShareIntent(intent)
    }
}

fun String.parseAndNormalizeUri(): Uri = Uri.parse(this).let {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        it.normalizeScheme()
    } else {
        it
    }
}

fun Context.shareText(text: String, mimeType: String = "text/plain") {
    execShareIntent(Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, text)
        type = mimeType
    })
}

fun Context.shareFile(file: File, mimeType: String) {
    getUriForFile(file)?.let {
        shareUri(it, mimeType)
    }
}

private fun Context.getUriForFile(file: File): Uri? = if (
    Build.VERSION.SDK_INT < Build.VERSION_CODES.N
) {
    Uri.fromFile(file)
} else {
    FileProvider.getUriForFile(
        this,
        BuildConfig.APPLICATION_ID + ".provider",
        file
    )
}

private fun Context.shareUri(uri: Uri, mimeType: String) {
    execShareIntent(Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        type = mimeType
    })
}
