# Bean Validation 校验

Spring 提供了相应的 Bean Validation 实现：Java Bean Validation，并在 Spring MVC 中添加了自动校验，默认就会对 @Valid/@Validated 修饰的方法参数使用 Validator 来做校验逻辑。

## 全局校验

1. 数据校验不通过，抛出一个 MethodArgumentNotValidException 异常，默认情况下，Spring 会将该异常及其信息以错误码 400 。
2. 全局异常处理
```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResult handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
        return new BaseResult(CommonResultCode.ILLEGAL_PARAMETERS.getErrorCode(),
                "入参中的" + fieldError.getField() + fieldError.getDefaultMessage(), EagleEye.getTraceId());
    }

}
```
