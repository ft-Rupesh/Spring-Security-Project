package com.globex.repository;


import org.springframework.data.repository.CrudRepository;

import com.globex.model.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findByEmail(String email);

    UserEntity findByUserId(String userId);

    UserEntity deleteByUserId(String userId);
}
