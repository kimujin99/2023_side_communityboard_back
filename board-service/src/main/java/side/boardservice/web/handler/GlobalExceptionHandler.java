package side.boardservice.web.handler;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import side.boardservice.domain.Message;
import side.boardservice.domain.StatusEnum;

import java.nio.charset.Charset;
import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public ResponseEntity<Message> handleInternalServerErr(Exception e) {

        return createErrResponse(e, StatusEnum.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<Message> handleBadRequestErr(Exception e) {

        return createErrResponse(e, StatusEnum.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<Message> handleNotFoundErr(Exception e) {

        return createErrResponse(e, StatusEnum.NOT_FOUND);
    }

    public ResponseEntity<Message> createErrResponse(Exception e, StatusEnum status) {
        //응답 객체
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        //메시지 설정
        Message errMessage = new Message();
        errMessage.setStatus(status);
        errMessage.setMessage(e.getMessage());

        //상태코드 분기
        HttpStatus httpStatus = null;
        if(status == StatusEnum.NOT_FOUND) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (status == StatusEnum.BAD_REQUEST) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (status == StatusEnum.INTERNAL_SERVER_ERROR) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(errMessage, headers, httpStatus);
    }
}
