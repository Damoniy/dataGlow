package org.dataglow.util

import java.net.URL

object CloudStorageUtils {

    fun isValidURL(url: String?): Boolean {
        return !url.isNullOrEmpty() && !isExpired(url)
    }

    private fun isExpired(urlString: String?): Boolean {

        if(urlString == null) return true

        val url = URL(urlString)
        if(url.toURI().query.contains("Expires=")) {
            val expirationTime: Long = url.toURI().query.substring(69, 79).toLong()
            if(expirationTime >= System.currentTimeMillis() / 1000L) {
                return false
            }
            return true
        }
        return true
    }
}