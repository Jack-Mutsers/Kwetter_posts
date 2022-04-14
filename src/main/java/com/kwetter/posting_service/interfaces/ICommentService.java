package com.kwetter.posting_service.interfaces;

import com.kwetter.posting_service.objects.data_transfer_objects.CommentForAlterationDTO;
import com.kwetter.posting_service.objects.exceptions.UnauthorizedException;
import com.kwetter.posting_service.objects.models.Comment;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.List;

public interface ICommentService {
    Comment getComment(int id) throws NotFoundException;
    List<Comment> getComments(int post_id);
    boolean deleteComment(int id);
    Comment createComment(CommentForAlterationDTO alterationDTO, String sender);
    Comment updateComment(CommentForAlterationDTO alterationDTO, String sender) throws UnauthorizedException, NotFoundException, BadRequestException;
}
