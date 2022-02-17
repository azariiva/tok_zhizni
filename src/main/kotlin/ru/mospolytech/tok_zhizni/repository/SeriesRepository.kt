package ru.mospolytech.tok_zhizni.repository

import ru.mospolytech.tok_zhizni.entity.domain.Series
import ru.mospolytech.tok_zhizni.entity.domain.SeriesCreateRequest
import ru.mospolytech.tok_zhizni.entity.domain.SeriesUpdateRequest

interface SeriesRepository : CrudRepository<SeriesCreateRequest, SeriesUpdateRequest, Series> {
    fun find(ids: List<Long>): List<Series>
}