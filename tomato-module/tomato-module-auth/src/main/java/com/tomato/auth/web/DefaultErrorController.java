package com.tomato.auth.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 默认错误处理
 *
 * @author lizhifu
 * @since 2023/5/5
 */
@Controller
@Slf4j
public class DefaultErrorController implements ErrorController {
    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        String message = getErrorMessage(request);
        log.info("进入error页面，message{}", message);
        if (message.startsWith("[access_denied]")) {
            return new ModelAndView("access-denied");
        }
        return new ModelAndView("error", Map.of("message", message));
    }

    private String getErrorMessage(HttpServletRequest request) {
        return (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
    }
}
