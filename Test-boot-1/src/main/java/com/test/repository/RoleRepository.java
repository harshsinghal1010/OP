package com.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
