package com.anilog.api.repository;

import com.anilog.api.domain.Post;
import com.anilog.api.request.PostSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepositoryCustom{
    List<Post> getList(PostSearch postSearch);
}
