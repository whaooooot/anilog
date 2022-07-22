package com.anilog.api.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle(){
        //서비스의 정책을 넣지 마세요! 절대!
        //return this.title.substring(0, 10);  //XXXXX
        return this.title;
    }

    private String title;

    @Lob
    private String content;



}
