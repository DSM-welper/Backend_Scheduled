package welper.welper_scheduled.repository

import org.springframework.data.jpa.repository.JpaRepository
import welper.welper_scheduled.domain.OpenAPIField

interface OpenAPIFieldRepository : JpaRepository<OpenAPIField, String> {
    fun existsByApiIdAndFieldContent(apiId: String, fieldContent: String):Boolean?
}