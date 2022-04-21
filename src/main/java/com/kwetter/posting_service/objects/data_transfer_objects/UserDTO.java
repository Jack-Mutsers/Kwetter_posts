package com.kwetter.posting_service.objects.data_transfer_objects;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    private String localId;
    private String displayName;
}
