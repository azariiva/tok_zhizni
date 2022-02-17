package ru.mospolytech.tok_zhizni.repository

import ru.mospolytech.tok_zhizni.entity.domain.PharmaceuticalForm
import ru.mospolytech.tok_zhizni.entity.domain.PharmaceuticalFormCreateRequest
import ru.mospolytech.tok_zhizni.entity.domain.PharmaceuticalFormUpdateRequest

interface PharmaceuticalFormsRepository :
    CrudRepository<PharmaceuticalFormCreateRequest, PharmaceuticalFormUpdateRequest, PharmaceuticalForm>