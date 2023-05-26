package org.dataglow.rest.service

import org.dataglow.rest.dto.MangaViewDTO
import org.dataglow.rest.repository.MangaRepository
import org.dataglow.util.CloudStorageUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class MangaService(
        @Autowired private val mangaRepository: MangaRepository,
        @Autowired private val cloudStorageService: CloudStorageService
) {

    fun getLatest(): ResponseEntity<*> {

        val latest = mangaRepository.findLatest()
        val mangaList = ArrayList<MangaViewDTO>()

        for(manga in latest) {
            if(!CloudStorageUtils.isValidURL(manga.logoUrl)) {
                manga.logoUrl = cloudStorageService.generateSignedURL(manga.mangaId).toString()
                updateLogoUrl(manga.logoUrl, manga.mangaId)
            }
            mangaList.add(manga)
        }

        return ResponseEntity.ok(latest)
    }

    fun updateLogoUrl(signedUrl: String?, mangaId: Int) {
        mangaRepository.updateLogo(signedUrl, mangaId)
    }
}