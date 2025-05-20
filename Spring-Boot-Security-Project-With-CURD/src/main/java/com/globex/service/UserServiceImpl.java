package com.globex.service;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.globex.model.RoleEntity;
import com.globex.model.UserDto;
import com.globex.model.UserEntity;
import com.globex.model.mapper.ModelMapperSingleton;
import com.globex.repository.RoleRepository;
import com.globex.repository.UserRepository;
import com.globex.security.UserPrincipal;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        ModelMapper mapper = ModelMapperSingleton.getInstance();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity newUser = mapper.map(userDto, UserEntity.class);
        Collection<RoleEntity> rolesEntity=new HashSet<>();
       for(String role:userDto.getRole()){
           RoleEntity roleEntity=roleRepository.findByName(role);
           if(roleEntity!=null){
               rolesEntity.add(roleEntity);
           }
       }
     newUser.setRoles(rolesEntity);
        repo.save(newUser);

        UserDto returnValue = mapper.map(newUser, UserDto.class);

        return returnValue;
    }

    @Override
    public UserDto getUserDetailsBYEmail(String email) {
        UserEntity entity = repo.findByEmail(email);
        return new ModelMapper().map(entity, UserDto.class);
    }

    public List<UserDto> getAllUsers() {
        ModelMapper mapper = ModelMapperSingleton.getInstance();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<UserEntity> users = (List<UserEntity>) repo.findAll();
        return users.stream().map(user ->
                mapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(String userId) {
        UserEntity entity = repo.findByUserId(userId);
        return ModelMapperSingleton.getInstance().map(entity, UserDto.class);
    }

    @Transactional
    @Override
    public UserDto deleteUserDetailsByID(String userId) {
        UserEntity entity = repo.findByUserId(userId);
        if (entity == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        repo.delete(entity);
        return ModelMapperSingleton.getInstance().map(entity, UserDto.class);

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity user = repo.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException("Email Id Not Found");
        // return new User(user.getEmail(),user.getPassword(),true,true,true,true,new ArrayList<>());
        return new UserPrincipal(user);
    }
}
