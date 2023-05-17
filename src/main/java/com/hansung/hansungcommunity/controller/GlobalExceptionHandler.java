package com.hansung.hansungcommunity.controller;

import com.hansung.hansungcommunity.exception.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 전역 Exception 핸들러
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AdoptNotFoundException.class, BoardNotFoundException.class, BookmarkNotFoundException.class, PartyNotFoundException.class, ReplyNotFoundException.class, SkillNotFoundException.class, SummaryNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler({DuplicateStudentException.class, InvalidAccessException.class, ParentReplyMismatchException.class, RecruitmentCompletedException.class})
    public ResponseEntity<ErrorResponse> handleDuplicateStudentException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class ErrorResponse {
        String message;
    }

}
