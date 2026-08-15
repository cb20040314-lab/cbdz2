package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器。
 *
 * <p>当控制器参数上的 {@code @Valid} 校验失败时，统一返回格式化的错误 JSON，
 * 避免将 Spring 默认的异常信息直接返回给前端。</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理请求参数校验失败的异常。
     *
     * <p>例如 {@code User.name} 标记了 {@code @NotBlank}，但前端提交空姓名时，
     * Spring 会抛出 {@link MethodArgumentNotValidException}，并由此方法处理。</p>
     *
     * @param e 参数校验失败时由 Spring 抛出的异常
     * @return HTTP 状态码为 400、包含具体错误提示的统一响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<String>> handleValidation(
            MethodArgumentNotValidException e) {

        // 从校验结果中取出第一个字段错误的提示，例如“姓名不能为空”。
        String message = e.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        // 返回 400（请求参数不合法），并将错误提示放入 Result 的 message 字段。
        return ResponseEntity.status(400)
                .body(Result.error(message));
    }
}
