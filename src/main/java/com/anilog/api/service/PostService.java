package com.anilog.api.service;

import com.anilog.api.domain.Post;
import com.anilog.api.repository.PostRepository;
import com.anilog.api.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
       //return postRepository.save(post); //Post데이터 그대로 응답
       //repository.save(postCreate);

    }
}
