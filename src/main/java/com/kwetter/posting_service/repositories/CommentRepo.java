package com.kwetter.posting_service.repositories;

import com.kwetter.posting_service.objects.models.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepo extends CrudRepository<Comment, Integer>{
    Comment findById(int id);
    List<Comment> findAllByPostId(int post_id);
    void deleteById(int id);
}
