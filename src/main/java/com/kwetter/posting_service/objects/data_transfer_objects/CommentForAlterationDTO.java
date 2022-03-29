package com.kwetter.posting_service.objects.data_transfer_objects;

import com.kwetter.posting_service.helpers.tools.Helper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentForAlterationDTO {
    private int id;
    private UUID sender;
    private int post_id;
    private int comment_id;
    private String message;
    private LocalDateTime creation_date;

    public boolean validateForUpdate(){ return !( this.id == 0 || (post_id == 0 && comment_id == 0) || !validateForCreation()); } // true == invalid data, false == correct data

    public boolean validateForCreation(){ return !( this.sender == null || Helper.IsEmpty(this.message) ); } // true == invalid data, false == correct data

    public void setId(int id) {
        this.id = id;
    }
}
