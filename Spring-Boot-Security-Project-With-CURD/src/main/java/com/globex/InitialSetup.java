package com.globex;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.globex.model.AuthorityEntity;
import com.globex.model.RoleEntity;
import com.globex.model.UserEntity;
import com.globex.repository.AuthorityRepository;
import com.globex.repository.RoleRepository;
import com.globex.repository.UserRepository;
import com.globex.shared.Authority;
import com.globex.shared.Roles;
import com.globex.shared.Utills;

import jakarta.transaction.Transactional;

public class InitialSetup {
	@Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    Utills utills;

    @Transactional
    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event){
  AuthorityEntity readAuthority=    createAuthority(Authority.READ_AUTHORITY.name());
   AuthorityEntity writeAuthority=   createAuthority(Authority.WRITE_AUTHORITY.name());
     AuthorityEntity deleteAuthority= createAuthority(Authority.DELETE_AUTHORITY.name());

      createRole(Roles.ROLE_USER.name(), Arrays.asList(readAuthority,writeAuthority));
        RoleEntity roleAdmin=createRole(Roles.ROLE_ADMIN.name(),Arrays.asList(readAuthority,writeAuthority,deleteAuthority));
       if(roleAdmin==null)return;
        UserEntity adminUser=new UserEntity();
        adminUser.setFirstName("Rupesh");
        adminUser.setLastName("Mishra");
        adminUser.setEmail("rupesh@gamil.com");
        adminUser.setUserId(utills.generateUserId(30));
        adminUser.setPassword(bCryptPasswordEncoder.encode("12456798"));
        adminUser.setRoles(Arrays.asList(roleAdmin));


        userRepository.save(adminUser);

    }

    @Transactional
   private AuthorityEntity createAuthority(String name){
        AuthorityEntity entity =authorityRepository.findByName(name);
        if(entity ==null) {
            entity = new AuthorityEntity(name);
            authorityRepository.save(entity);
        }
return entity;
    }

    @Transactional
    private RoleEntity createRole(String name, Collection<AuthorityEntity> authorities){
      RoleEntity entity=roleRepository.findByName(name);
      if(entity==null) {
          entity = new RoleEntity(name);
          entity.setAuthorities(authorities);
          roleRepository.save(entity);
      }
      return entity;
    }


}
