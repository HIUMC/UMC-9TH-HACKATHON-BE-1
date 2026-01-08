package com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.handler;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.ApiResponse;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.code.BaseErrorCode;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.code.GeneralErrorCode;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.exception.GeneralException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class GeneralExceptionAdvice {
    // 애플리케이션에서 발생하는 커스텀 예외를 처리
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Void>> handleException(GeneralException ex) {
        return ResponseEntity.status(ex.getCode().getStatus()).body(ApiResponse.onFailure(ex.getCode(), null));
    }

    // 그 외 정의되지 않은 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception ex) {
        BaseErrorCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(code.getStatus()).body(ApiResponse.onFailure(code, ex.getMessage()));
    }

    // 컨트롤러 메서드에서 @Valid 애노테이션을 사용하여 DTO의 유효성 검사를 수행
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {
        // 검사 실패한 필드와 그에 대한 메시지를 저장하는 Map
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        GeneralErrorCode code = GeneralErrorCode.VALID_FAIL;
        ApiResponse<Map<String, String>> response = ApiResponse.onFailure(code, errors);

        // 에러 코드, 메시지와 함께 errors를 반환
        return ResponseEntity.status(code.getStatus()).body(response);
    }

    // @RequestParam, @PathVariable 등에 대한 커스텀 유효성 검사 실패 처리
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ApiResponse<Map<String, String>>> handleConstraintViolationException(
            ConstraintViolationException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        for (ConstraintViolation<?> violation : violations) {
            // 파라미터 이름 추출 (예: getReviews.page -> page)
            String propertyPath = violation.getPropertyPath().toString();
            String fieldName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
            errors.put(fieldName, violation.getMessage());
        }

        GeneralErrorCode code = GeneralErrorCode.VALID_FAIL;
        ApiResponse<Map<String, String>> response = ApiResponse.onFailure(code, errors);

        return ResponseEntity.status(code.getStatus()).body(response);
    }
}
