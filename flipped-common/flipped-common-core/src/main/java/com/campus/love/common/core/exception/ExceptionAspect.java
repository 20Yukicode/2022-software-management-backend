package com.campus.love.common.core.exception;

import com.campus.love.common.core.api.MessageModel;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExceptionAspect {

    /**
     * 业务异常apiException
     * @param e
     * @return
     */
    @ExceptionHandler(value = ApiException.class)
    public MessageModel<Object> apiException(ApiException e) {
        String message = e.getMessage() == null ? e.getError().getMessage() : e.getMessage();
        log.warn("业务异常:" + message);
        return Optional.ofNullable(e.getError())
                .map((error) -> MessageModel.result(error.getCode(), error.getMessage(), null))
                .orElseGet(() -> MessageModel.failed(e.getMessage()));
    }

    /**
     * 异常信息可能不止一个，所以需要处理list
     *
     * @param list
     * @return
     */
    private String validationHandle(List<FieldError> list) {
        return list.stream()
                .map(s -> s.getField() + s.getDefaultMessage())
                .collect(Collectors.toList())
                .toString();
    }

    /**
     * 校验参数异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public MessageModel<Object> validException(Exception e) {
        log.warn("校验参数异常:" + e.getMessage());
        BindingResult bindingResult = null;
        if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        } else {
            bindingResult = ((BindException) e).getBindingResult();
        }
        MessageModel<Object> model = null;
        if (bindingResult.hasErrors()) {
            model = Optional
                    .ofNullable(bindingResult.getFieldErrors())
                    .map(this::validationHandle)
                    .map(MessageModel::validateFailed)
                    .orElseGet(MessageModel::validateFailed);
        }
        return model;
    }

    /**
     * sql异常
     * @param e
     * @return
     */
    @ExceptionHandler(SQLException.class)
    public MessageModel<Object> sqlException(SQLException e) {
        log.warn("sql异常:" + e.getMessage());
        return MessageModel.failed(e.getMessage());
    }


}
