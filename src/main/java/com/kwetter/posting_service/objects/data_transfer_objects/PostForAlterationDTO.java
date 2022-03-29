package com.kwetter.posting_service.objects.data_transfer_objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostForAlterationDTO {
    private int id;
    private boolean visible = true;
    private UUID user_id;
    private int group_id;
    private boolean group = false;
    private String creation_date;
    private CommentForAlterationDTO message;

    public boolean validateForUpdate(){ return ( this.id == 0 || validateForCreation()) || message.validateForUpdate(); } // true == invalid data, false == correct data

    public boolean validateForCreation(){ return ( this.group_id == 0 || message == null || message.validateForCreation() ); } // true == invalid data, false == correct data

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreation_date(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(creation_date, formatter);
    }
}
