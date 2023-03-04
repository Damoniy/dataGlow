package org.dataglow.rest.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class MangaRepository(@Autowired private val gracefulJdbcTemplate: NamedParameterJdbcTemplate) {


}