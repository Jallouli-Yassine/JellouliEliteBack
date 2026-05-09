package tn.jallouli.elite.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.jallouli.elite.modules._UserRole.entity.RoleEntity;
import tn.jallouli.elite.modules._user.entity.UserEntity;
import tn.jallouli.elite.modules._user.repository.UserRepository;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//2
/* filtrage al user w token testhak jwt */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;
    public CustomUserDetailsService(UserRepository userRepo){
        this.userRepo=userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity u = userRepo.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found with email: " + username));
        return new User(u.getEmail(), u.getPassword(), mapRolesToAuthorities(u.getRole()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(RoleEntity userRole) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getRoleName().name()));
        return authorities;
    }
}
