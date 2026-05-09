package tn.jallouli.elite.modules._user.mapper;

import org.springframework.stereotype.Component;
import tn.jallouli.elite.modules._user.dto.UserRequest;
import tn.jallouli.elite.modules._user.dto.UserResponse;
import tn.jallouli.elite.modules._user.entity.RoleName;
import tn.jallouli.elite.modules._user.entity.UserEntity;

@Component
public class UserMapper {


    public UserEntity toEntity(UserRequest request) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(request.getFirstName());
        userEntity.setLastName(request.getLastName());
        userEntity.setPhone(request.getPhone());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(request.getPassword());
        userEntity.setEmail(request.getEmail());
        return userEntity;
    }

    public UserResponse toResponse(UserEntity userEntity) {
        return new UserResponse(userEntity.getEmail(), userEntity.getPhone());
    }


}