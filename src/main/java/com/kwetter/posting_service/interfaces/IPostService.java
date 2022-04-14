package com.kwetter.posting_service.interfaces;

import com.kwetter.posting_service.objects.data_transfer_objects.PostForAlterationDTO;
import com.kwetter.posting_service.objects.exceptions.UnauthorizedException;
import com.kwetter.posting_service.objects.models.Post;

import javax.ws.rs.NotFoundException;
import java.util.List;

public interface IPostService {
    Post getPost(int id) throws NotFoundException;
    List<Post> getPostsByUser(String user_id);
    List<Post> getPostsByGroup(int group_id);
    boolean deletePost(int id);
    Post createPost(PostForAlterationDTO alterationDTO, String user);
    Post updatePost(PostForAlterationDTO alterationDTO, String user) throws UnauthorizedException, NotFoundException;
}
