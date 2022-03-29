package com.kwetter.posting_service.services;

import com.kwetter.posting_service.helpers.logger.LoggerService;
import com.kwetter.posting_service.interfaces.ICommentService;
import com.kwetter.posting_service.objects.data_transfer_objects.CommentForAlterationDTO;
import com.kwetter.posting_service.objects.models.Comment;
import com.kwetter.posting_service.repositories.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements ICommentService {
    @Autowired
    private CommentRepo commentRepo;

    public Comment getComment(int id) {
        Comment comment = commentRepo.findById(id);
        return comment;
    }

    public List<Comment> getComments(int post_id) {
        List<Comment> comments = commentRepo.findAllByPostId(post_id);
        return comments;
    }

    public boolean deleteComment(int id) {
        try{
            commentRepo.deleteById(id);
            return true;
        }catch (Exception ex){
            LoggerService.warn(ex.getMessage());
            return false;
        }
    }

    public Comment createComment(CommentForAlterationDTO alterationDTO) {
        try{
            Comment comment = new Comment(alterationDTO);
            Comment newObject = commentRepo.save(comment);
            return newObject;
        }catch (Exception ex){
            LoggerService.warn(ex.getMessage());
            return null;
        }
    }

    public Comment updateComment(CommentForAlterationDTO alterationDTO) {
        try{
            Comment comment = new Comment(alterationDTO);
            Comment updatedObject = commentRepo.save(comment);
            return updatedObject;
        }catch (Exception ex){
            LoggerService.warn(ex.getMessage());
            return null;
        }
    }
}
