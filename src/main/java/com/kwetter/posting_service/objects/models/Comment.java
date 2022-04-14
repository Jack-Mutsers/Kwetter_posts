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
import java.time.format.DateTimeFormatter;

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
    private String sender;

    @Column(name = "post_id")
    private int postId;

    @Column(nullable = true)
    private int comment_id;

    @Type(type="text")
    private String message;
    private LocalDateTime creation_date;

    public String getCreation_dateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return creation_date.format(formatter);
    }

    public Comment(CommentForAlterationDTO DTO, String sender) {
        this.id = DTO.getId();
        this.sender = sender;
        this.postId = DTO.getPost_id();
        this.comment_id = DTO.getComment_id();
        this.message = DTO.getMessage();
        this.creation_date = DTO.getCreation_date();
    }
}


