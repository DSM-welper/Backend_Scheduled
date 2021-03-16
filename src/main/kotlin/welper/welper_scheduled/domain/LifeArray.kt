//package welper.welper_scheduled.domain
//
//import javax.persistence.*
//
//@Entity
//class LifeArray(
//        @Id
//        @OneToOne(fetch = FetchType.LAZY)
//        @JoinColumn(name ="apiId")
//        val openApiPost: OpenApiPost,
//
//        isInFants: Boolean = false,
//        isChild: Boolean = false,
//        isTeenAge: Boolean = false,
//        isYouth: Boolean = false,
//        isMiddleAge: Boolean = false,
//        isOldAge: Boolean = false,
//
//) {
//    var isInFants = isInFants
//        private set
//    var isChild = isChild
//        private set
//    var isTeenAge = isTeenAge
//        private set
//    var isYouth = isYouth
//        private set
//    var isMiddleAge = isMiddleAge
//        private set
//    var isOldAge = isOldAge
//        private set
//
//    fun updateIsInFants(isInFants: Boolean) {
//        this.isInFants = isInFants
//    }
//
//    fun updateChild(isChild: Boolean) {
//        this.isChild = isChild
//    }
//
//    fun updateTeenAge(isTeenAge: Boolean) {
//        this.isTeenAge = isTeenAge
//    }
//
//    fun updateYouth(isYouth: Boolean) {
//        this.isYouth = isYouth
//    }
//
//    fun updateMiddleAge(isMiddleAge: Boolean) {
//        this.isMiddleAge = isMiddleAge
//    }
//
//    fun updateOldAge(isOldAge: Boolean) {
//        this.isOldAge = isOldAge
//    }
//}
