package com.anilog.api.controller;

import com.anilog.api.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
public class PostController {

    //Http Method
    //GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    // 글 등록
    //POST Method

    @PostMapping("/posts")
    public String post(@RequestBody PostCreate params) {

        /*
         * 테스트에서 param으로 넘김
         */

        //@RequestParam String title, @RequestParam String content
        //log.info("title={}, content={}", title, content);

        //@RequestParam Map<String, String> params
        //log.info("params={}", params);
        //String title = params.get("title");

        //PostCreate params //PostCreate 만들고 사용
        //log.info("params={}", params.toString());

        /*
         * 테스트에서 json값으로 넘김
         */
        //@RequestBody PostCreate params
        log.info("params={}", params.toString());

        return "Hello world";
    }
}
