package tn.jallouli.elite.modules._user.dto;

public class UserResponse {
    private String email;
    private String phoneNumber;

    public UserResponse() {}

    public UserResponse(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    String getEmail() {
        return email;
    }
    String getPhoneNumber() {
        return phoneNumber;
    }
}
