package com.anilog.api.service;

import com.anilog.api.domain.Post;
import com.anilog.api.repository.PostRepository;
import com.anilog.api.request.PostCreate;
import com.anilog.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate){
        //postCreate -> Entity 바꿈
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
       //return postRepository.save(post);  //Post데이터 그대로 응답
       //postRepository.save(post);         //저장하고 끝내기
    }

    public PostResponse get(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getList(){
        return postRepository.findAll().stream()
                .map(post -> PostResponse.builder()
                         .id(post.getId())
                         .title(post.getTitle())
                         .build())
                .collect(Collectors.toList());
    }


}
