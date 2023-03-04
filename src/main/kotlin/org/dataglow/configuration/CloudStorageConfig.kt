package org.dataglow.configuration

import com.google.auth.Credentials
import com.google.auth.oauth2.GoogleCredentials

object CloudStorageConfig {
    val credentials = GoogleCredentials.fromStream(this.javaClass.getResourceAsStream("/credentials.json"))
    val bucketName = "legiaodomanga"
    val projectId = "mangalegion"
}