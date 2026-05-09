package tn.jallouli.elite.modules._user.dto;

import tn.jallouli.elite.modules._user.entity.RoleName;


public class UserRequest {

    private Long idUser;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String password;

    private RoleName roleName;

    public UserRequest(){}

    public UserRequest(Long idUser, String firstName, String lastName, String email, String phone, String password, RoleName roleName) {
        this.idUser = idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.roleName = roleName;
    }


    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
