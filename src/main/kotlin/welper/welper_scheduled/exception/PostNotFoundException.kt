package welper.welper_scheduled.exception

import org.springframework.http.HttpStatus
import welper.welper_scheduled.exception.handler.CommonException

class PostNotFoundException(

) : CommonException(
        code = "POST_NOTFOUND",
        message = "일치하는 게시물을 찾을 수 없습니다. [Not found email = [email]",
        status = HttpStatus.BAD_REQUEST,
)