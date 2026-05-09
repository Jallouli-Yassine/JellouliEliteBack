package tn.jallouli.elite.modules._user.service.impl;

import org.springframework.stereotype.Service;
import tn.jallouli.elite.modules._user.entity.UserEntity;
import tn.jallouli.elite.modules._user.repository.UserRepository;
import tn.jallouli.elite.modules._user.service.UserInterface;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserInterface {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<List<UserEntity>> getAllUsers() {
        return Optional.of(userRepository.findAll());
    }
}
