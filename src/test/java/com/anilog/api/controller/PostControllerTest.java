package com.anilog.api.controller;

import com.anilog.api.domain.Post;
import com.anilog.api.repository.PostRepository;
import com.anilog.api.request.PostCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청시 Hello world출력")
    void test() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);   //json String을 만들어 줌

        //expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )  // application/json
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
        //db -> post 1개등록
    }

    @Test
    @DisplayName("/posts 요청시 title 값은 필수다.")
    void test2() throws Exception {
        //given
        PostCreate request = PostCreate.builder() //title null인경우
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);   //json String을 만들어 줌

        //expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )  // application/json
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400")) //jsonPath사용
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장된다..")
    void test3() throws Exception {
        //given
        PostCreate request = PostCreate.builder() //title null인경우
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);   //json String을 만들어 줌

        //before
        //postRepository.deleteAll(  );
        //위에 clean() 이 테스트메소드할때마다 실행됨

        //expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        //db -> post 1개등록

        //then
        assertEquals(1L,postRepository.count());
        //db -> post 최종 test와 test3 2개등록됨 그래서 2로하고 전체돌려야 성공

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());  //db에 들어있는지 확인
        assertEquals("내용입니다.", post.getContent());  //db에 들어있는지 확인
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        // given
        Post post  = Post.builder()
                .title("123456789012345")
                .content("bar")
                .build();
        postRepository.save(post);

        // 클라이언트 요구사항
        // json응답에서 title 길이를 최대 10글자로 해주세요.
        /**
         * {id: ..., title: ...}
         */

        // expected (when + then)
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test5() throws Exception {
        // given
        List<Post> requestPosts = IntStream.range(1, 31) //1~30  > 와 같은 코드 for(int i =1; i<31; i++)
                .mapToObj(i -> Post.builder()
                        .title("제목 " + i)
                        .content("내용 " + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        /**
         * [{id:..., title:...}, {id:..., title:...}]
         */
        // expected (when + then)
        mockMvc.perform(get("/posts?page=1&sort=id,desc")  //&size=5  or yml에 default-page-size: 5
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(5)))
                .andExpect(jsonPath("$.[0].id").value(30))
                .andExpect(jsonPath("$.[0].title").value("제목 30"))
                .andExpect(jsonPath("$.[0].content").value("내용 30"))
                .andDo(print());
    }

}
