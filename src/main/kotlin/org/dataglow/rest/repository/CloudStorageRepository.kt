package org.dataglow.rest.repository

import com.google.api.gax.paging.Page
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
import java.util.concurrent.TimeUnit

@Repository
class CloudStorageRepository {
    private val storage = StorageOptions.newBuilder().setCredentials(CloudStorageConfig.credentials).setProjectId(CloudStorageConfig.projectId).build().service

    fun uploadImage(objectName: String, filePath: String) {
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

    fun genMangaLogoSignedURL(mangaId: Int): URL {
        val blob = storage.get(CloudStorageConfig.bucketName, "$mangaId/logo.jpg")
        return blob.signUrl(1, TimeUnit.DAYS)
    }

    fun getMangaChapterPageList(mangaId: Int, chapterId: Int): List<MangaPage> {
        val blobs: Page<Blob> = storage.list(CloudStorageConfig.bucketName, Storage.BlobListOption.prefix("$mangaId/$chapterId"))
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
