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
import java.util.UUID;

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
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID writer; // post writer

    @Column(name = "group_id", nullable = true)
    private int groupId;
    private boolean group;
    private LocalDateTime creation_date;

    public String getCreation_date() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(creation_date);
    }

    public Post(PostForAlterationDTO postDTO) {
        this.id = postDTO.getId();
        this.visible = postDTO.isVisible();
        this.writer = postDTO.getUser_id();
        this.groupId = postDTO.getGroup_id();
        this.group = postDTO.isGroup();
        this.creation_date = postDTO.getCreation_date();
    }
}


