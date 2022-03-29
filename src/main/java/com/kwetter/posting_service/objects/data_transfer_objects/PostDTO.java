package com.kwetter.posting_service.objects.data_transfer_objects;

import com.kwetter.posting_service.objects.models.Comment;
import com.kwetter.posting_service.objects.models.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PostDTO {
    private int id;
    private UUID user_Id;
    private int group_Id;
    private String creation_date;
    private List<Comment> comments;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.user_Id = post.getWriter();
        this.group_Id = post.getGroupId();
        this.creation_date = post.getCreation_date();
    }

    public PostDTO(){}

}
