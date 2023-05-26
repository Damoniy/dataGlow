package org.dataglow.domain

data class Manga(
    val mangaName: String,
    val description: String = "None",
    val logoSignedUrl: String
)
