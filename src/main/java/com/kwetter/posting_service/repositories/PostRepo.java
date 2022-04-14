package com.kwetter.posting_service.repositories;

import com.kwetter.posting_service.objects.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepo extends CrudRepository<Post, Integer>{
    Post findById(int id);
    List<Post> findAllByWriter(String user_Id);
    List<Post> findAllByGroupId(int group_Id);
    void deleteById(int id);
}
