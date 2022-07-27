package com.anilog.api.controller;

import com.anilog.api.domain.Post;
import com.anilog.api.request.PostCreate;
import com.anilog.api.response.PostResponse;
import com.anilog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


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
     * /posts/{postId} -> 글 한개만 조회 API (1개의 글을 가져오는 기능)
     * /posts -> getMapping 사용 여러개의 글을 조회 API
     */
    @GetMapping("posts/{postId}")
    public PostResponse get(@PathVariable Long postId){
        // Request 클래스 (PostCreate)
        // Response 클래스 (PostResponse)

        return postService.get(postId);
    }

    @GetMapping("/posts")
    public List<PostResponse> getList(Pageable pageable)
    {
        return postService.getList(pageable);

    }



}
