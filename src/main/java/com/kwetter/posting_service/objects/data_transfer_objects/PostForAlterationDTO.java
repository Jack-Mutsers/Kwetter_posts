package com.kwetter.posting_service.objects.data_transfer_objects;

import com.kwetter.posting_service.helpers.tools.Helper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostForAlterationDTO {
    private int id = 0;
    private boolean visible = true;
    private int group_id;
    private boolean group = false;
    private String creation_date;
    private String message;

    public boolean validateForUpdate(){ return ( this.id == 0 || validateForCreation()) || Helper.IsEmpty(message); } // true == invalid data, false == correct data

    public boolean validateForCreation(){ return ( this.group_id == 0 || message == null || Helper.IsEmpty(message) ); } // true == invalid data, false == correct data

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreation_date(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(creation_date, formatter);
    }
}
