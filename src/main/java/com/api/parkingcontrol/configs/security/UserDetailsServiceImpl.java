package com.api.parkingcontrol.configs.security;

import com.api.parkingcontrol.models.UserModel;
import com.api.parkingcontrol.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

  final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserModel userModel = userRepository.findByUsername(username).orElseThrow(() ->
        new UsernameNotFoundException("User not found: " + username));

 //    Retorna o usuário sem as Roles.
//    return userModel;

    // retorna o usuário comm as roles
    return new User(
        userModel.getUsername(),
        userModel.getPassword(),
        true,
        true,
        true,
        true,
        userModel.getAuthorities()
    );
  }
}
