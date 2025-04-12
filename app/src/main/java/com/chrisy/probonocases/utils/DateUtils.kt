package com.chrisy.probonocases.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    private val dateTimeFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())

    private fun formatDate(timestamp: Long?): String {
        return if (timestamp != null) {
            dateFormat.format(Date(timestamp))
        } else {
            "Not set"
        }
    }

    fun formatDateTime(timestamp: Long?): String {
        return if (timestamp != null) {
            dateTimeFormat.format(Date(timestamp))
        } else {
            "Not set"
        }
    }

    fun getRelativeTimeSpan(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp

        return when {
            diff < 60 * 1000 -> "just now"
            diff < 60 * 60 * 1000 -> "${diff / (60 * 1000)} minutes ago"
            diff < 24 * 60 * 60 * 1000 -> "${diff / (60 * 60 * 1000)} hours ago"
            diff < 7 * 24 * 60 * 60 * 1000 -> "${diff / (24 * 60 * 60 * 1000)} days ago"
            else -> formatDate(timestamp)
        }
    }
}