package welper.welper_scheduled.repository

import org.springframework.data.jpa.repository.JpaRepository
import welper.welper_scheduled.domain.CopyApiCategory
import welper.welper_scheduled.domain.OpenApICategory

interface CopyApiCategoryRepository: JpaRepository<CopyApiCategory, String> {

}