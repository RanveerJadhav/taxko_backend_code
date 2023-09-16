package com.Tasko.Registration.error;

import com.Tasko.Registration.Entity.ErrorMessage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException exception, WebRequest request)
    {
        ErrorMessage massage=new ErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(massage);
    }

    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<ErrorMessage> userAlreadyExist(UserAlreadyExist exist,WebRequest request)
    {
        ErrorMessage massage=new ErrorMessage(HttpStatus.NOT_FOUND,exist.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(massage);
    }


    @ExceptionHandler(UserName_Paswword_Incorrect.class)
    public ResponseEntity<ErrorMessage> userName_Paswword_Incorrect(UserName_Paswword_Incorrect incorrect,WebRequest request)
    {
        ErrorMessage massage=new ErrorMessage(HttpStatus.UNAUTHORIZED,incorrect.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(massage);
    }

    @ExceptionHandler(OtpNotVaild.class)
    public ResponseEntity<ErrorMessage> otpNotVaild(OtpNotVaild incorrect,WebRequest request)
    {
        ErrorMessage massage=new ErrorMessage(HttpStatus.UNAUTHORIZED,incorrect.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(massage);
    }
//
//    @ExceptionHandler(MultipartException.class)
//    public ResponseEntity<ErrorMessage> handleMultipartException(MultipartException ex, RedirectAttributes attributes)
//    {
//        ErrorMessage massage=new ErrorMessage(HttpStatus.UNAUTHORIZED,ex.getMessage());
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(massage);
//    }
@ExceptionHandler(MultipartException.class)
public ResponseEntity<String> handleMaxUploadSizeExceeded() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Maximum upload size exceeded");
}
//    @ExceptionHandler(FileAlreadyExists.class)
//    public ResponseEntity<ErrorMessage> handleMultipartException(FileAlreadyExists ex,WebRequest request)
//    {
//        ErrorMessage massage=new ErrorMessage(HttpStatus.UNAUTHORIZED,ex.getMessage());
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(massage);
//    }
@ExceptionHandler(FileAlreadyExists.class)
public ResponseEntity<String> handleMultipartException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("File Already Exists");
}

//    @ExceptionHandler(EmailMandatoryException.class)
//    public ResponseEntity<String> EmailMandatoryException()
//    {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email Already Exists");
//    }

    @ExceptionHandler(EmailMandatoryException.class)
    public ResponseEntity<ErrorMessage> EmailMandatoryException(EmailMandatoryException incorrect,WebRequest request)
    {
        ErrorMessage massage=new ErrorMessage(HttpStatus.UNAUTHORIZED,incorrect.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(massage);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorMessage> ClientNotFoundException(ClientNotFoundException clientNotFoundException, WebRequest request)
    {
        ErrorMessage massage=new ErrorMessage(HttpStatus.NOT_FOUND,clientNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(massage);
    }

}