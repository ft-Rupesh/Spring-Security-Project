package com.globex.security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.globex.model.AuthorityEntity;
import com.globex.model.RoleEntity;
import com.globex.model.UserEntity;

public class UserPrincipal implements UserDetails {

    private UserEntity userEntity;
    @Autowired
    public UserPrincipal(UserEntity userEntity){

        this.userEntity=userEntity;
    }
    @Override

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authority=new HashSet<>();
        Collection<AuthorityEntity> authorityEntity=new HashSet<>();

      Collection<RoleEntity> roles=userEntity.getRoles();
      if(roles==null)return authority;

      roles.forEach((role)->{
          authority.add(new SimpleGrantedAuthority(role.getName()));
          authorityEntity.addAll(role.getAuthorities());
      });

      authorityEntity.forEach((authoritie)->{
          authority.add(new SimpleGrantedAuthority(authoritie.getName()));
      });


        return authority;
    }

    @Override
    public String getPassword() {
        return this.userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
