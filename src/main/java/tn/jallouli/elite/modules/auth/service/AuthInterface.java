package tn.jallouli.elite.modules.auth.service;

import tn.jallouli.elite.modules.auth.dto.AuthResponse;
import tn.jallouli.elite.modules.auth.dto.LoginRequest;
import tn.jallouli.elite.modules.auth.dto.RegisterRequest;

public interface AuthInterface {
    AuthResponse login(LoginRequest loginDTO);
    void createDefaultAdminAccount();
    //regiser :
    void register(RegisterRequest newUser);

}

