package com.globex.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.globex.model.UserDto;

public interface UserService extends UserDetailsService {
    public UserDto createUser(UserDto userDto);
    public UserDto getUserDetailsBYEmail(String emial);
    public List<UserDto> getAllUsers();

  public  UserDto getUserById(String userId);
  public UserDto deleteUserDetailsByID(String userId);

}
