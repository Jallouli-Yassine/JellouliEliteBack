package tn.jallouli.elite.modules._user.service;

import tn.jallouli.elite.modules._user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserInterface {
    Optional<List<UserEntity>> getAllUsers();
}
