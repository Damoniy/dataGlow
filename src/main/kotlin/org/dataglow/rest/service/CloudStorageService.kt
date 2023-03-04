package org.dataglow.rest.service

import com.google.auth.Credentials
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import org.dataglow.domain.manga.MangaPage
import org.dataglow.rest.repository.CloudStorageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
class CloudStorageService(@Autowired val repository: CloudStorageRepository) {

    fun uploadImage(imageName: String, filePath: String): ResponseEntity<*> {
        repository.uploadImage(imageName, filePath);
        return ResponseEntity.ok("")
    }

    fun getMangaCHapterPageList(): ResponseEntity<*> {
        return ResponseEntity.ok(repository.getMangaChapterPageList())
    }
}