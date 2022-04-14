package com.kwetter.posting_service.objects.models;

import com.kwetter.posting_service.objects.data_transfer_objects.PostForAlterationDTO;
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
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean visible;

    @Column(length = 36)
    private String writer; // post writer

    @Column(name = "group_id", nullable = true)
    private int groupId;
    private boolean group;

    @Type(type="text")
    private String message;

    private LocalDateTime creation_date;

    public String getCreation_date() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return creation_date.format(formatter);
    }

    public Post(PostForAlterationDTO postDTO, String user) {
        this.id = postDTO.getId();
        this.visible = postDTO.isVisible();
        this.writer = user;
        this.groupId = postDTO.getGroup_id();
        this.group = postDTO.isGroup();
        this.creation_date = postDTO.getCreation_date();
        this.message = postDTO.getMessage();
    }
}


