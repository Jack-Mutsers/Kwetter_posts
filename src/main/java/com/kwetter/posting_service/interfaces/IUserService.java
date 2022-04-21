package com.kwetter.posting_service.interfaces;

import com.kwetter.posting_service.objects.data_transfer_objects.UserDTO;

public interface IUserService {
    UserDTO returnUser(String user_id);
}
