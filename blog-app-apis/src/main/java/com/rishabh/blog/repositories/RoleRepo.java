package com.rishabh.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rishabh.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
