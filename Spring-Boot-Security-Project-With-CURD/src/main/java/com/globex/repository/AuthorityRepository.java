package com.globex.repository;

import org.springframework.data.repository.CrudRepository;

import com.globex.model.AuthorityEntity;

public interface AuthorityRepository extends CrudRepository<AuthorityEntity,Long> {
    AuthorityEntity findByName(String name);
}
