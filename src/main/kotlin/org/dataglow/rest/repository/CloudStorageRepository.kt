package org.dataglow.rest.repository

import com.google.api.gax.paging.Page
import com.google.auth.Credentials
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.Blob
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import org.dataglow.configuration.CloudStorageConfig
import org.dataglow.domain.manga.MangaPage
import org.springframework.stereotype.Repository
import java.net.URL
import java.nio.file.Paths
import java.util.Scanner
import java.util.concurrent.TimeUnit

@Repository
class CloudStorageRepository {
    fun uploadImage(objectName: String, filePath: String) {
        val storage: Storage = StorageOptions.newBuilder().setCredentials(CloudStorageConfig.credentials).setProjectId(CloudStorageConfig.projectId).build().service;
        val blobId = BlobId.of(CloudStorageConfig.bucketName, objectName)
        val blobInfo = BlobInfo.newBuilder(blobId).build()

        if(storage.get(CloudStorageConfig.bucketName, objectName) == null) {
            val precondition = Storage.BlobWriteOption.doesNotExist()
            storage.createFrom(blobInfo, Paths.get(filePath), precondition)
            return;
        }

        val precondition = Storage.BlobWriteOption.generationMatch(storage.get(CloudStorageConfig.bucketName, objectName).generation)
        storage.createFrom(blobInfo, Paths.get(filePath), precondition)
    }

    fun getMangaChapterPageList(): List<MangaPage> {
        val credentials: Credentials = GoogleCredentials.fromStream(this.javaClass.getResourceAsStream("/credentials.json"))
        val storage: Storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId("mangalegion").build().service
        val blobs: Page<Blob> = storage.list(CloudStorageConfig.bucketName, Storage.BlobListOption.prefix("child/"))
        val pages = ArrayList<MangaPage>()
        var i = 0

        for(blob in blobs.iterateAll()) {
            i++
            pages.add(
                MangaPage(
                    i,
                    blob.signUrl(20, TimeUnit.SECONDS).toString()
                )
            )
        }

        for(page in pages) {
            println(page.imageUrl)
            val signedUrl = URL(page.imageUrl)
            if (signedUrl.toURI().query.contains("Expires=")) {
                val expirationTime: Long = signedUrl.toURI().query.substring(69, 79).toLong()
                if (expirationTime <= System.currentTimeMillis()) {
                    println("The signed URL is still valid.")
                } else {
                    println("The signed URL has expired.")
                }
            } else {
                println("The signed URL is invalid.")
            }
        }

        return pages
    }

 }
