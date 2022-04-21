package com.kwetter.posting_service.services;

import com.google.gson.Gson;
import com.kwetter.posting_service.helpers.configurations.MessagingConfig;
import com.kwetter.posting_service.helpers.tools.Helper;
import com.kwetter.posting_service.interfaces.IUserService;
import com.kwetter.posting_service.objects.data_transfer_objects.UserDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private RabbitTemplate template;

    public UserDTO returnUser(String user_id){
        UserDTO user = new UserDTO();
        user.setLocalId(user_id);

        String response = (String) template.convertSendAndReceive(MessagingConfig.EXCHANGE, "user.request", user);

        if(Helper.IsEmpty(response)){
            return null;
        }

        Gson gson = new Gson();
        return gson.fromJson(response, UserDTO.class);
    }
}
