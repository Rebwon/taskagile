package com.rebwon.taskagile.web.apis;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rebwon.taskagile.web.results.ApiResult;
import com.rebwon.taskagile.web.results.Result;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({Exception.class})
  protected ResponseEntity<ApiResult> handle(RuntimeException ex) {
    String errorReferenceCode = UUID.randomUUID().toString();
    log.error("Unhandled exception error [code=" + errorReferenceCode + "]", ex);
    return Result.serverError("Sorry, there is an error on the server side.", errorReferenceCode);
  }

  @ExceptionHandler({MaxUploadSizeExceededException.class})
  protected ResponseEntity<ApiResult> handle(MaxUploadSizeExceededException ex) {
    return Result.failure("File exceeded maximum size limit");
  }
}
