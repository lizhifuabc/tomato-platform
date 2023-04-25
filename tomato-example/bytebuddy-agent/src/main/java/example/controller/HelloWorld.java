package example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloWorld
 *
 * @author lizhifu
 * @since 2023/4/25
 */
@RestController
public class HelloWorld {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
