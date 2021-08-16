package com.bekzad.cryptotracker.ui.coins

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

/**
 * This one for shortening toast messages
 */
fun String.toast(context: Context?) = Toast.makeText(context, this, Toast.LENGTH_SHORT).show()

/**
 * Shows no network alert
 */
fun Context.showInternetAlert() {
    AlertDialog.Builder(this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle("Internet Connection Alert")
        .setMessage("Please connect to network")
        .setPositiveButton("Close") { dialogInterface, i ->
        }.show()
}
