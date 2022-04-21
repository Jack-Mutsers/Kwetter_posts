package com.kwetter.posting_service.services;

import com.kwetter.posting_service.helpers.logger.LoggerService;
import com.kwetter.posting_service.interfaces.IPostService;
import com.kwetter.posting_service.objects.data_transfer_objects.PostForAlterationDTO;
import com.kwetter.posting_service.objects.exceptions.UnauthorizedException;
import com.kwetter.posting_service.objects.models.Post;
import com.kwetter.posting_service.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
public class PostService implements IPostService {
    @Autowired
    private PostRepo postRepo;

    public Post getPost(int id) throws NotFoundException {
        Post post = postRepo.findById(id);

        if(post == null){
            throw new NotFoundException("The requested post with id: " + id + " could not be found");
        }

        return post;
    }

    public List<Post> getPostsByUser(String user_id) {
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

    public Post createPost(PostForAlterationDTO alterationDTO, String user) {
        Post post = new Post(alterationDTO, user);
        Post newObject = postRepo.save(post);
        return newObject;
    }

    public Post updatePost(PostForAlterationDTO alterationDTO, String user) throws UnauthorizedException, NotFoundException {
        Post originalPost = this.getPost(alterationDTO.getId());

        if(!user.equals(originalPost.getWriter())){
            throw new UnauthorizedException("The user: \""+ user +"\" does not have permission to alter post: " + originalPost.getId());
        }

        originalPost.setMessage(alterationDTO.getMessage());

        Post updatedObject = postRepo.save(originalPost);
        return updatedObject;
    }

}
