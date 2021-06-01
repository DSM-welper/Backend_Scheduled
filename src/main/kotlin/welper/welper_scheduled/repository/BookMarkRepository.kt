package welper.welper_scheduled.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import welper.welper_scheduled.domain.BookMark
import welper.welper_scheduled.domain.OpenApiPost

interface BookMarkRepository : JpaRepository<BookMark, Int> {
    @Transactional
    fun deleteAllOpenApiPost(post: OpenApiPost)
}