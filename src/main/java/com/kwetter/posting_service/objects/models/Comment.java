package com.kwetter.posting_service.objects.models;

import com.kwetter.posting_service.objects.data_transfer_objects.CommentForAlterationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.UUID;

@SuppressWarnings("WeakerAccess")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 36)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID sender;

    @Column(name = "post_id")
    private int postId;

    @Column(nullable = true)
    private int comment_id;
    private String message;
    private LocalDateTime creation_date;

    public String getCreation_dateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(creation_date);
    }

    public Comment(CommentForAlterationDTO DTO) {
        this.id = DTO.getId();
        this.sender = DTO.getSender();
        this.postId = DTO.getPost_id();
        this.comment_id = DTO.getComment_id();
        this.message = DTO.getMessage();
        this.creation_date = DTO.getCreation_date();
    }
}


