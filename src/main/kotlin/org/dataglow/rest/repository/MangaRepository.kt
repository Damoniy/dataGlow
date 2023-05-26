package org.dataglow.rest.repository

import org.dataglow.rest.dto.MangaViewDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class MangaRepository(@Autowired private val gracefulJdbcTemplate: NamedParameterJdbcTemplate) {

    fun findLatest(): List<MangaViewDTO> {
        return gracefulJdbcTemplate.query("select nr_sequencia mangaId, nm_manga nomeManga, ds_url_logo logoUrl from manga", DataClassRowMapper(MangaViewDTO::class.java))
    }

    fun updateLogo(logoUrl: String?, mangaId: Int) {
        println("Updating")
        val parameterSource = MapSqlParameterSource()
        parameterSource.addValue("nr_seq_manga", mangaId)
        gracefulJdbcTemplate.update("update manga set ds_url_logo = '$logoUrl', dt_atualizacao = current_date where nr_sequencia = :nr_seq_manga", parameterSource)
    }
}

