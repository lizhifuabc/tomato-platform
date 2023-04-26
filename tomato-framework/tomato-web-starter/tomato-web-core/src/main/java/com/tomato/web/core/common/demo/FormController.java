package com.tomato.web.core.common.demo;

import com.tomato.common.resp.Resp;
import com.tomato.web.core.common.demo.entity.Demo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * multipart/form-data 请求头
 * 当为GET请求时,入参对象或者单属性均可与传入的name键值对一一对应
 * 当为POST请求时,浏览器把form数据封装到http body中,不可用@RequestBody注解修饰接收实体.
 * @author lizhifu
 * @date 2021/9/15
 */
@RestController
@RequestMapping(value = "form", headers = "content-type=multipart/form-data")
public class FormController {
    private Logger log = LoggerFactory.getLogger(FormController.class);
    /**
     * GET 传参没有注解修饰,实体接收
     * @param demo
     * @return
     */
    @GetMapping(value = "demo1")
    public Resp demo1(Demo demo) {
        return Resp.of(demo);
    }

    /**
     * GET请求 传参没有注解修饰
     * 也可用 @RequestParam 取别名
     * @param demo
     * @return
     */
    @GetMapping(value = "demo2")
    public Resp demo2(@RequestParam(value = "demo", required = false) String demo) {
        return Resp.of(demo);
    }

    /**
     * POST请求 传参没有注解修饰
     * 传入Body里,参数名为入参的属性名称,文件传输可分开指定字段,也可直接在实体中定义.
     * @param demo
     * @param files
     * @return
     */
    @PostMapping(value = "demo3")
    public Resp demo3(Demo demo, @RequestParam("files") MultipartFile[] files) {
        return Resp.buildSuccess();
    }
}
