package welper.welper_scheduled.domain

import javax.persistence.*

@Entity

class OpenApiPost       (
        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val servId: String?,
        val inqNUm: String?,
        val jurMnofNm: String?,
        val jurOrgNm: String?,
        val servDgst: String?,
        val servDtlLink: String?,
        val servNm: String?,
        val svcfrstRegTs: String?,
//        @OneToMany(fetch = FetchType.LAZY)
//        @JoinColumn(name = "api_post_id")
//        val openApiContent: String,

        )