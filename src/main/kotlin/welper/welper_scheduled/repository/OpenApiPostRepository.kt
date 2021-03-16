package welper.welper_scheduled.repository

import org.springframework.data.jpa.repository.JpaRepository
import welper.welper_scheduled.domain.OpenApiPost

interface OpenApiPostRepository : JpaRepository<OpenApiPost, String> {
}