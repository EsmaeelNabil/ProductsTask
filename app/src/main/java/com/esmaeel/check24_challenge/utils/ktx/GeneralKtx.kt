package com.esmaeel.check24_challenge.utils.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.format.DateFormat
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.*

fun Fragment.showToast(message: String?) = message?.let {
    makeToast(requireContext(), message)
}

fun makeToast(context: Context, message: String?) =
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

fun Activity.showToast(message: String?) = message?.let {
    makeToast(this, message)
}

fun Activity.openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(intent)
}

fun Long.getDate(): String {
    val cal: Calendar = Calendar.getInstance(Locale.ENGLISH)
    cal.timeInMillis = this * 1000
    return DateFormat.format("dd.MM.yyyy", cal).toString()
}
