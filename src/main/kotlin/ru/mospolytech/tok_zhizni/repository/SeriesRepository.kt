package ru.mospolytech.tok_zhizni.repository

import ru.mospolytech.tok_zhizni.entity.Series
import ru.mospolytech.tok_zhizni.entity.SeriesCreateRequest
import ru.mospolytech.tok_zhizni.entity.SeriesUpdateRequest

interface SeriesRepository {
    fun find(): List<Series>
    fun find(ids: List<Long>): List<Series>
    fun find(id: Long): Series?
    fun create(createRequest: SeriesCreateRequest): Series
    fun update(id: Long, updateRequest: SeriesUpdateRequest)
    fun delete(id: Long)
}