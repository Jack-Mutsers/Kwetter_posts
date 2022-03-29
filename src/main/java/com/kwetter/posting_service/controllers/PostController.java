package com.kwetter.posting_service.controllers;

import com.kwetter.posting_service.interfaces.ICommentService;
import com.kwetter.posting_service.interfaces.IPostService;
import com.kwetter.posting_service.objects.data_transfer_objects.PostDTO;
import com.kwetter.posting_service.objects.data_transfer_objects.PostForAlterationDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.kwetter.posting_service.helpers.tools.Helper.emptyIfNull;

@Controller
@RequestMapping("/post")
public class PostController {
    @Autowired
    private final IPostService postService = new PostService();

    @Autowired
    private final ICommentService commentService = new CommentService();

    @GetMapping(path = "/")
    public @ResponseBody ResponseEntity<Object> getPost(HttpServletRequest request, @RequestBody Map<String, Integer> id_map){
        int post_id = id_map.get("id");
        Post post = postService.getPost(post_id);

        if (post == null){
            return new ResponseEntity<>("Requested post could not be found.", HttpStatus.NOT_FOUND);
        }

        PostDTO postDTO = getPostDTO(post);

        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/user_posts")
    public @ResponseBody ResponseEntity<Object> getPostsFromUser(HttpServletRequest request, @RequestBody Map<String, UUID> id_map){
        UUID user_id = id_map.get("id");
        List<Post> posts = postService.getPostsByUser(user_id);

        List<PostDTO> dtoList = getPostDTOList(posts);

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping(path = "/group_posts")
    public @ResponseBody ResponseEntity<Object> getPostsByUser(HttpServletRequest request, @RequestBody Map<String, Integer> id_map){
        int group_id = id_map.get("group_id");
        List<Post> posts = postService.getPostsByGroup(group_id);

        List<PostDTO> dtoList = getPostDTOList(posts);

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @DeleteMapping(path = "/")
    public @ResponseBody
    ResponseEntity<String> deletePost(HttpServletRequest request, @RequestBody Map<String, Integer> id_map) {
        int id = id_map.get("id");
        boolean success = postService.deletePost(id);

        if (!success) {
            return new ResponseEntity<>("something went wrong while deleting the post.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Post has been deleted successfully.", HttpStatus.OK);
    }

    @PostMapping(path="/")
    public @ResponseBody ResponseEntity<Object> createPost(@RequestBody PostForAlterationDTO alterationDTO) {
        if(!alterationDTO.validateForCreation()){
            return new ResponseEntity<>("Post could not be created with the supplied information", HttpStatus.CONFLICT);
        }

        alterationDTO.setId(0); // set id to 0 to avoid any unwanted updates
        Post post = postService.createPost(alterationDTO);

        if (post == null){
            return new ResponseEntity<>("something went wrong wile creating a new Post", HttpStatus.CONFLICT);
        }

        PostDTO postDTO = getPostDTO(post);

        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @PutMapping(path ="/")
    public @ResponseBody ResponseEntity<Object> updatePost(HttpServletRequest request, @RequestBody PostForAlterationDTO alterationDTO) {
        if(!alterationDTO.validateForUpdate()){
            return new ResponseEntity<>("Post could not be updated with the supplied information.", HttpStatus.CONFLICT);
        }

        Post post = postService.updatePost(alterationDTO);

        if (post == null){
            return new ResponseEntity<>("Something went wrong while updating the post", HttpStatus.CONFLICT);
        }

        PostDTO postDTO = getPostDTO(post);

        return new ResponseEntity<>(postDTO, HttpStatus.OK);
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
