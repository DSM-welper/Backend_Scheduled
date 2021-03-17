package welper.welper_scheduled.repository

import org.springframework.data.jpa.repository.JpaRepository
import welper.welper_scheduled.domain.OpenApICategory
import welper.welper_scheduled.domain.OpenApiPost

interface OpenApiCategoryRepository : JpaRepository<OpenApICategory, String> {
    fun existsByCategoryNameAndOpenApiPost(categoryName: String, openApiPost: OpenApiPost):Boolean
}