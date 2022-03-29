package com.kwetter.posting_service.objects.data_transfer_objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO {
    private Boolean status = false;
    private String message;
    private Object body;

    public ResponseDTO(Boolean status, String message){
        this.status = status;
        this.message = message;
    }

    public ResponseDTO(Boolean status, Object body){
        this.status = status;
        this.body = body;
    }

    public ResponseDTO(){}
}
