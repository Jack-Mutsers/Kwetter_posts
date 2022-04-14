package com.kwetter.posting_service.services;

import com.kwetter.posting_service.helpers.logger.LoggerService;
import com.kwetter.posting_service.interfaces.ICommentService;
import com.kwetter.posting_service.objects.data_transfer_objects.CommentForAlterationDTO;
import com.kwetter.posting_service.objects.exceptions.UnauthorizedException;
import com.kwetter.posting_service.objects.models.Comment;
import com.kwetter.posting_service.objects.models.Post;
import com.kwetter.posting_service.repositories.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
public class CommentService implements ICommentService {
    @Autowired
    private CommentRepo commentRepo;

    public Comment getComment(int id) throws NotFoundException{
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

    public Comment createComment(CommentForAlterationDTO alterationDTO, String sender) {
        try{
            Comment comment = new Comment(alterationDTO, sender);
            Comment newObject = commentRepo.save(comment);
            return newObject;
        }catch (Exception ex){
            LoggerService.warn(ex.getMessage());
            return null;
        }
    }

    public Comment updateComment(CommentForAlterationDTO alterationDTO, String sender) throws NotFoundException, UnauthorizedException, BadRequestException {
        Comment originalComment = this.getComment(alterationDTO.getId());

        if(!sender.equals(originalComment.getSender())){
            throw new UnauthorizedException("The user: \""+ sender +"\" does not have permission to alter the comment: "+ alterationDTO.getId() +" on post: " + originalComment.getPostId());
        }

        if(originalComment.getPostId() != alterationDTO.getPost_id()){
            throw new BadRequestException("Comment: " + alterationDTO.getId() + " does not belong to post: " + alterationDTO.getPost_id());
        }

        originalComment.setMessage(alterationDTO.getMessage());

        Comment updatedObject = commentRepo.save(originalComment);
        return updatedObject;
    }
}
