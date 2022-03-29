package com.kwetter.posting_service.interfaces;

import com.kwetter.posting_service.objects.data_transfer_objects.CommentForAlterationDTO;
import com.kwetter.posting_service.objects.models.Comment;

import java.util.List;

public interface ICommentService {
    Comment getComment(int id);
    List<Comment> getComments(int post_id);
    boolean deleteComment(int id);
    Comment createComment(CommentForAlterationDTO alterationDTO);
    Comment updateComment(CommentForAlterationDTO alterationDTO);
}
