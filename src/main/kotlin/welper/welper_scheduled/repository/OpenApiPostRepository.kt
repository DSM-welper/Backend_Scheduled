package welper.welper_scheduled.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import welper.welper_scheduled.domain.OpenApiPost

interface OpenApiPostRepository : JpaRepository<OpenApiPost, String> {
    @Query("select c from  OpenApiPost c left join CopyApiPost o on c.servId=o.servId where o.servId is null")
    fun onlyComparisonApiPost(): List<OpenApiPost>

    @Query("select o from  OpenApiPost c left join CopyApiPost o on c.servId=o.servId where c.servId is null")
    fun onlyComparisonCopyPost(): MutableList<OpenApiPost>
}