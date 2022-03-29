package com.kwetter.posting_service.controllers;

import com.kwetter.posting_service.interfaces.ICommentService;
import com.kwetter.posting_service.objects.data_transfer_objects.CommentDTO;
import com.kwetter.posting_service.objects.data_transfer_objects.CommentForAlterationDTO;
import com.kwetter.posting_service.objects.models.Comment;
import com.kwetter.posting_service.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private final ICommentService commentService = new CommentService();

    @DeleteMapping(path = "/")
    public @ResponseBody
    ResponseEntity<String> deleteComment(HttpServletRequest request, @RequestBody Map<String, Integer> id_map) {
        int id = id_map.get("id");
        boolean success = commentService.deleteComment(id);

        if (!success) {
            return new ResponseEntity<>("something went wrong while deleting the comment.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Comment has been deleted successfully.", HttpStatus.OK);
    }

    @PostMapping(path="/")
    public @ResponseBody ResponseEntity<Object> createComment(@RequestBody CommentForAlterationDTO alterationDTO) {
        if(!alterationDTO.validateForCreation()){
            return new ResponseEntity<>("Comment could not be created with the supplied information", HttpStatus.CONFLICT);
        }

        alterationDTO.setId(0); // set id to 0 to avoid any unwanted updates
        Comment comment = commentService.createComment(alterationDTO);

        if (comment == null){
            return new ResponseEntity<>("something went wrong wile creating the new comment", HttpStatus.CONFLICT);
        }

        CommentDTO commentDTO = new CommentDTO(comment);

        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }

    @PutMapping(path ="/")
    public @ResponseBody ResponseEntity<Object> updateComment(HttpServletRequest request, @RequestBody CommentForAlterationDTO alterationDTO) {
        if(!alterationDTO.validateForUpdate()){
            return new ResponseEntity<>("Comment could not be updated with the supplied information.", HttpStatus.CONFLICT);
        }

        Comment comment = commentService.updateComment(alterationDTO);

        if (comment == null){
            return new ResponseEntity<>("Something went wrong while updating the comment", HttpStatus.CONFLICT);
        }

        CommentDTO commentDTO = new CommentDTO(comment);

        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

}
