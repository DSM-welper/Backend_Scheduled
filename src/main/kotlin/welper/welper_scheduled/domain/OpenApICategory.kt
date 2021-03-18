package welper.welper_scheduled.domain

import welper.welper_scheduled.attribute.Category
import javax.persistence.*

@Entity
@Table(name = "open_api_category4")
class OpenApICategory(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,

        val categoryName: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "postId")
        val openApiPost: OpenApiPost,

        )
