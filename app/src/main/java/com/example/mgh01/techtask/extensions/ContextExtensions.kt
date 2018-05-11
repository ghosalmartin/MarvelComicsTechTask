package com.example.mgh01.techtask.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.mgh01.techtask.R

fun Context.openBrowser(uri: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    } else {
        Toast.makeText(this, R.string.no_browser, Toast.LENGTH_LONG).show()
    }
}