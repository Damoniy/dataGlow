package org.dataglow.rest.controller

import org.dataglow.rest.service.CloudStorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class CloudStorageController(@Autowired val cloudStorageService: CloudStorageService) {

    @PostMapping("/v1/storage/upload")
    fun upload(): ResponseEntity<*> {
        return this.javaClass.getResource("/files/test.png")?.let { cloudStorageService.uploadImage("child/test.png", it.path) }!!
    }

    @GetMapping("/v1/storage/mangaChapter")
    fun getMangaChapter(): ResponseEntity<*> {
        return cloudStorageService.getMangaCHapterPageList()
    }
}