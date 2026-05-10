package tn.jallouli.elite.modules.auth.service;

import tn.jallouli.elite.modules.auth.dto.*;

public interface AuthInterface {
    AuthResponse login(LoginRequest loginDTO);
    void createDefaultAdminAccount();
    //regiser :
    void register(RegisterRequest newUser);
    void forgotPassword(ForgotPasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
}

