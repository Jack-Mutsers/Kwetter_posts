package com.kwetter.posting_service.services;

import com.kwetter.posting_service.helpers.logger.LoggerService;
import com.kwetter.posting_service.interfaces.IPostService;
import com.kwetter.posting_service.objects.data_transfer_objects.PostForAlterationDTO;
import com.kwetter.posting_service.objects.models.Post;
import com.kwetter.posting_service.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostService implements IPostService {
    @Autowired
    private PostRepo postRepo;

    public Post getPost(int id) {
        Post post = postRepo.findById(id);
        return post;
    }

    public List<Post> getPostsByUser(UUID user_id) {
        List<Post> posts = postRepo.findAllByWriter(user_id);
        return posts;
    }

    public List<Post> getPostsByGroup(int group_id) {
        List<Post> posts = postRepo.findAllByGroupId(group_id);
        return posts;
    }

    public boolean deletePost(int id) {
        try{
            postRepo.deleteById(id);
            return true;
        }catch (Exception ex){
            LoggerService.warn(ex.getMessage());
            return false;
        }
    }

    public Post createPost(PostForAlterationDTO alterationDTO) {
        try{
            Post post = new Post(alterationDTO);
            Post newObject = postRepo.save(post);
            return newObject;
        }catch (Exception ex){
            LoggerService.warn(ex.getMessage());
            return null;
        }
    }

    public Post updatePost(PostForAlterationDTO alterationDTO) {
        try{
            Post post = new Post(alterationDTO);
            Post updatedObject = postRepo.save(post);
            return updatedObject;
        }catch (Exception ex){
            LoggerService.warn(ex.getMessage());
            return null;
        }
    }
}
