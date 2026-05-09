package tn.jallouli.elite.modules.auth.dto;

import lombok.Data;
import tn.jallouli.elite.modules._user.entity.RoleName;

@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private RoleName roleName;
}