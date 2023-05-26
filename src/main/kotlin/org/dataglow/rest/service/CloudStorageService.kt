package org.dataglow.rest.service

import org.dataglow.rest.repository.CloudStorageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.net.URL


@Service
class CloudStorageService(@Autowired val repository: CloudStorageRepository) {

    fun uploadImage(imageName: String, filePath: String): ResponseEntity<*> {
        repository.uploadImage(imageName, filePath);
        return ResponseEntity.ok("")
    }

    fun generateSignedURL(mangaId: Int): URL {
        return repository.genMangaLogoSignedURL(mangaId)
    }

    fun getMangaChapterPageList(): ResponseEntity<*> {
        return ResponseEntity.ok(repository.getMangaChapterPageList(1, 1))
    }
}