package com.kwetter.posting_service.interfaces;

import com.kwetter.posting_service.objects.data_transfer_objects.PostForAlterationDTO;
import com.kwetter.posting_service.objects.models.Post;

import java.util.List;
import java.util.UUID;

public interface IPostService {
    Post getPost(int id);
    List<Post> getPostsByUser(UUID user_id);
    List<Post> getPostsByGroup(int group_id);
    boolean deletePost(int id);
    Post createPost(PostForAlterationDTO alterationDTO);
    Post updatePost(PostForAlterationDTO alterationDTO);
}
