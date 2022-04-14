package com.kwetter.posting_service.controllers;

import com.kwetter.posting_service.interfaces.ICommentService;
import com.kwetter.posting_service.interfaces.IPostService;
import com.kwetter.posting_service.objects.data_transfer_objects.PostDTO;
import com.kwetter.posting_service.objects.data_transfer_objects.PostForAlterationDTO;
import com.kwetter.posting_service.objects.exceptions.UnauthorizedException;
import com.kwetter.posting_service.objects.models.Comment;
import com.kwetter.posting_service.objects.models.Post;
import com.kwetter.posting_service.services.CommentService;
import com.kwetter.posting_service.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.kwetter.posting_service.helpers.tools.Helper.emptyIfNull;

@Controller
@RequestMapping("/post")
public class PostController {
    @Autowired
    private final IPostService postService = new PostService();

    @Autowired
    private final ICommentService commentService = new CommentService();

    @GetMapping(path = "/get/{post_id}")
    public @ResponseBody ResponseEntity<Object> getPost(HttpServletRequest request, @PathVariable int post_id){
        Post post = postService.getPost(post_id);

        if (post == null){
            return new ResponseEntity<>("Requested post could not be found.", HttpStatus.NOT_FOUND);
        }

        PostDTO postDTO = getPostDTO(post);

        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/user_posts{user_id}")
    public @ResponseBody ResponseEntity<Object> getPostsFromUser(HttpServletRequest request, @PathVariable String user_id){
        List<Post> posts = postService.getPostsByUser(user_id);

        List<PostDTO> dtoList = getPostDTOList(posts);

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping(path = "/group_posts/{group_id}")
    public @ResponseBody ResponseEntity<Object> getPostsByUser(HttpServletRequest request, @PathVariable int group_id){
        List<Post> posts = postService.getPostsByGroup(group_id);

        List<PostDTO> dtoList = getPostDTOList(posts);

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody
    ResponseEntity<String> deletePost(HttpServletRequest request, @PathVariable int id) {
        boolean success = postService.deletePost(id);

        if (!success) {
            return new ResponseEntity<>("something went wrong while deleting the post.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Post has been deleted successfully.", HttpStatus.OK);
    }

    @PostMapping(path="/create")
    public @ResponseBody ResponseEntity<Object> createPost(HttpServletRequest request, @RequestBody PostForAlterationDTO alterationDTO) {
        String user_id = request.getHeader("x-auth-user-id");
        if(!alterationDTO.validateForCreation()){
            return new ResponseEntity<>("Post could not be created with the supplied information", HttpStatus.CONFLICT);
        }

        alterationDTO.setId(0); // set id to 0 to avoid any unwanted updates
        Post post = postService.createPost(alterationDTO, user_id);

        if (post == null){
            return new ResponseEntity<>("something went wrong wile creating a new Post", HttpStatus.CONFLICT);
        }

        PostDTO postDTO = getPostDTO(post);

        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @PutMapping(path ="/update")
    public @ResponseBody ResponseEntity<Object> updatePost(HttpServletRequest request, @RequestBody PostForAlterationDTO alterationDTO) {
        String user_id = request.getHeader("x-auth-user-id");
        if(!alterationDTO.validateForUpdate()){
            return new ResponseEntity<>("Post could not be updated with the supplied information.", HttpStatus.CONFLICT);
        }

        try {
            Post post = postService.updatePost(alterationDTO, user_id);

            if (post == null){
                return new ResponseEntity<>("Something went wrong while updating the post", HttpStatus.CONFLICT);
            }

            PostDTO postDTO = getPostDTO(post);

            return new ResponseEntity<>(postDTO, HttpStatus.OK);
        }catch (NotFoundException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }catch (UnauthorizedException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public PostDTO getPostDTO(Post post){
        PostDTO postDTO = new PostDTO(post);
        List<Comment> comments = commentService.getComments(post.getId());
        postDTO.setComments(comments);

        return postDTO;
    }

    public List<PostDTO> getPostDTOList(List<Post> posts){
        List<PostDTO> dtoList = new ArrayList<>();
        for (Post post : emptyIfNull(posts))
        {
            PostDTO postDTO = this.getPostDTO(post);
            dtoList.add(postDTO);
        }

        return dtoList;
    }
}
