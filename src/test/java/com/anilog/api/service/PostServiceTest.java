package com.anilog.api.service;

import com.anilog.api.domain.Post;
import com.anilog.api.repository.PostRepository;
import com.anilog.api.request.PostCreate;
import com.anilog.api.request.PostSearch;
import com.anilog.api.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {


    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1(){
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        // when
        postService.write(postCreate);

        // then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());  //db에 들어있는지 확인
        assertEquals("내용입니다.", post.getContent());  //db에 들어있는지 확인
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2(){
        // given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(requestPost);   //저장하고

        // when
        PostResponse response = postService.get(requestPost.getId());  //저장한거 가져옴

        // then
        assertNotNull(response);
        assertEquals(1L, postRepository.count()); //갯수확인 (전체테스트시 여러개라 오류 -> BeforeEach의 clean메소드로 지워주기)
        assertEquals("foo", response.getTitle());
        assertEquals("bar", response.getContent());
    }

    @Test
    @DisplayName("글 1페이지 조회 pageable")
    void test3(){
        // given
        List<Post> requestPosts = IntStream.range(1, 31) //1~30  > 와 같은 코드 for(int i =1; i<31; i++)
                .mapToObj(i -> Post.builder()
                        .title("제목 " + i)
                        .content("내용 " + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        // sql -> select, limit, offset
        /**
        Pageable pageableRequest = PageRequest.of(0, 5, Sort.Direction.DESC, "id"); //수동이라 직접 지정해줘야함
         */

        // when
        /**
        List<PostResponse> posts = postService.getList(pageableRequest);
        */

        // then
        /**
        assertEquals(5L, posts.size()); //갯수확인
        assertEquals("제목 30", posts.get(0).getTitle());
        assertEquals("제목 26", posts.get(4).getTitle());
        */
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test4(){
        // given
        List<Post> requestPosts = IntStream.range(0, 20) //1~20  > 와 같은 코드 for(int i =1; i<31; i++)
                .mapToObj(i -> Post.builder()
                        .title("제목 " + i)
                        .content("내용 " + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        // sql -> select, limit, offset

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();

        // when
        List<PostResponse> posts = postService.getList(postSearch);

        // then
        assertEquals(10L, posts.size()); //갯수확인
        assertEquals("제목 19", posts.get(0).getTitle());



    }



}