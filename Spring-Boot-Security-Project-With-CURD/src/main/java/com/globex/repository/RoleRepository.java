package com.globex.repository;

import org.springframework.data.repository.CrudRepository;

import com.globex.model.RoleEntity;

public interface RoleRepository extends CrudRepository<RoleEntity,Long> {
    RoleEntity findByName(String name);
}
