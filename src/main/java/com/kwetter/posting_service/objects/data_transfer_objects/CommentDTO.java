package com.kwetter.posting_service.objects.data_transfer_objects;

import com.kwetter.posting_service.objects.models.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDTO {
    private int id;
    private UUID sender;
    private int post_id;
    private int comment_id;
    private String message;
    private LocalDateTime creation_date;

    public CommentDTO(Comment comment){
        this.id = comment.getId();
        this.sender = comment.getSender();
        this.post_id = comment.getPostId();
        this.comment_id = comment.getComment_id();
        this.message = comment.getMessage();
        this.creation_date = comment.getCreation_date();
    }
}
