package com.melzzz.vaxsafe_project1.features.vaccines.data.mapper

import com.melzzz.vaxsafe_project1.features.vaccines.data.model.VaccineDto
import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.Vaccine
import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.VaccineStatus

fun VaccineDto.toDomain(): Vaccine {
    return Vaccine(
        id = this.id,
        name = this.name,
        laboratory = this.laboratory,
        date = this.date,
        status = VaccineStatus.valueOf(this.status)
    )
}

fun Vaccine.toDto(): VaccineDto {
    return VaccineDto(
        id = this.id,
        name = this.name,
        laboratory = this.laboratory,
        date = this.date,
        status = this.status.name
    )
}