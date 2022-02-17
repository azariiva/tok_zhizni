package ru.mospolytech.tok_zhizni.repository

interface CrudRepository<C,U,R> {
    fun find(): List<R>
    fun find(id: Long): R?
    fun create(createRequest: C): R
    fun update(id: Long, updateRequest: U)
    fun delete(id: Long)
}