package welper.welper_scheduled.domain

import welper.welper_scheduled.attribute.Category
import javax.persistence.*

@Entity
@Table(name = "copy_api_category")
class CopyApiCategory(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,

        val categoryName: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "postId")
        val openApiPost: OpenApiPost,

        )

