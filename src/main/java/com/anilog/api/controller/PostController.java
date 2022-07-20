package com.anilog.api.controller;

import com.anilog.api.domain.Post;
import com.anilog.api.request.PostCreate;
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
        // Case1. 저장한 데이터 Entity -> response로 응답하기 ({"id":1,"title":"제목입니다.","content":"내용입니다."})
        // Case2. 저장한 데이터의 primary_id -> response로 응답하기
        //           Client에서는 수신한 id를 post조회 API를 통해서 글 데이터를 수신받음 {"postId":1}
        // Case3. 응답 필요 없음 -> 클라이언트에서 모든 POST(글) 데이터 context를 잘 관리함
        // Bad Case : 서버에서 -> 반드시 이렇게 할겁니다! fix
        //           -> 서버에서 차라리 유연하게 대응하는 좋습니다. -> 코드를 잘 짜야함
        //           -> 한 번에 일괄적으로 잘 처리되는 케이스가 없습니다 - > 잘 관리하는 형태가 중요합니다.
        postService.write(request);
        //return Map.of();  //Map<String, String>  보통 응닶 안내려보낼때가많음
    }

    /**
     * /posts -> 글 전체 조회(검색+페이징)
     * /posts/{postId} -> 글 한개만 조회
     */

    @GetMapping("posts/{postId}")
    public Post get(@PathVariable(name = "postId") Long id){
        Post post = postService.get(id);

        return post;
    }



}
