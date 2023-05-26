package org.dataglow.rest.controller

import org.dataglow.rest.service.MangaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller()
class MangaController(@Autowired private val mangaService: MangaService) {

    @GetMapping("/v1/manga/latest")
    fun getLatest(): ResponseEntity<*> {
        return mangaService.getLatest();
    }
}