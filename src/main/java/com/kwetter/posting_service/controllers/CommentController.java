package com.kwetter.posting_service.controllers;

import com.kwetter.posting_service.helpers.tools.Helper;
import com.kwetter.posting_service.interfaces.ICommentService;
import com.kwetter.posting_service.objects.data_transfer_objects.CommentDTO;
import com.kwetter.posting_service.objects.data_transfer_objects.CommentForAlterationDTO;
import com.kwetter.posting_service.objects.exceptions.UnauthorizedException;
import com.kwetter.posting_service.objects.models.Comment;
import com.kwetter.posting_service.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private final ICommentService commentService = new CommentService();

    @DeleteMapping(path = "/delete/{post_id}")
    public @ResponseBody
    ResponseEntity<String> getComments(HttpServletRequest request, @PathVariable int post_id) {
        try{
            List<Comment> comments = commentService.getComments(post_id);

            Helper.emptyIfNull(comments);

            return new ResponseEntity<>("Comment has been deleted successfully.", HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody
    ResponseEntity<String> deleteComment(HttpServletRequest request, @PathVariable int id) {
        boolean success = commentService.deleteComment(id);

        if (!success) {
            return new ResponseEntity<>("something went wrong while deleting the comment.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Comment has been deleted successfully.", HttpStatus.OK);
    }

    @PostMapping(path="/create")
    public @ResponseBody ResponseEntity<Object> createComment(HttpServletRequest request, @RequestBody CommentForAlterationDTO alterationDTO) {
        if(!alterationDTO.validateForCreation()){
            return new ResponseEntity<>("Comment could not be created with the supplied information", HttpStatus.CONFLICT);
        }

        String sender = request.getHeader("x-auth-user-id");

        alterationDTO.setId(0); // set id to 0 to avoid any unwanted updates
        Comment comment = commentService.createComment(alterationDTO, sender);

        if (comment == null){
            return new ResponseEntity<>("something went wrong wile creating the new comment", HttpStatus.CONFLICT);
        }

        CommentDTO commentDTO = new CommentDTO(comment);

        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }

    @PutMapping(path ="/update")
    public @ResponseBody ResponseEntity<Object> updateComment(HttpServletRequest request, @RequestBody CommentForAlterationDTO alterationDTO) {
        if(!alterationDTO.validateForUpdate()){
            return new ResponseEntity<>("Comment could not be updated with the supplied information.", HttpStatus.CONFLICT);
        }

        String sender = request.getHeader("x-auth-user-id");

        try{
            Comment comment = commentService.updateComment(alterationDTO, sender);

            if (comment == null){
                return new ResponseEntity<>("Something went wrong while updating the comment", HttpStatus.CONFLICT);
            }

            CommentDTO commentDTO = new CommentDTO(comment);

            return new ResponseEntity<>(commentDTO, HttpStatus.OK);
        }catch (NotFoundException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }catch (UnauthorizedException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }catch (BadRequestException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
