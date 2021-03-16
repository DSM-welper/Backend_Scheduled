package welper.welper_scheduled.domain

import welper.welper_scheduled.attribute.Field
import javax.persistence.*

@Entity
class OpenApIField(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int=0,
        val fieldName: Field,
//        @OneToMany(fetch = FetchType.LAZY)
//        @JoinColumn(name = "api_field_id")
//        val openApiContent: Set<OpenApiContent?>
        )