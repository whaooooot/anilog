package com.anilog.api.controller;

import com.anilog.api.domain.Post;
import com.anilog.api.request.PostCreate;
import com.anilog.api.response.PostResponse;
import com.anilog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //Http Method
    //GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT

    // 글 등록
    //POST Method
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        postService.write(request);
    }

    /**
     * /posts -> 글 전체 조회(검색+페이징)
     * /posts/{postId} -> 글 한개만 조회
     */
    @GetMapping("posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long id){
        // Request 클래스 (PostCreate)
        // Response 클래스 (PostResponse)

        PostResponse response = postService.get(id);
        // 응답 클래스를 분리하세요 (서비스 정책에 맞는)
        return response;
    }





}
