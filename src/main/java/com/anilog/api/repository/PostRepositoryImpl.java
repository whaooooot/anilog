package com.anilog.api.repository;

import com.anilog.api.domain.Post;
import com.anilog.api.domain.QPost;
import com.anilog.api.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.anilog.api.domain.QPost.*;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch){
        return jpaQueryFactory.selectFrom(post)  //Qpost꺼사용
                .limit(postSearch.getSize())  //10개제한
                .offset(postSearch.getOffset())
                .orderBy(post.id.desc())
                .fetch();
    }

}
