package com.codewithvarsha.blogapp2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithvarsha.blogapp2.entities.Role;

public interface RoleRepo  extends JpaRepository<Role, Integer>{

}
