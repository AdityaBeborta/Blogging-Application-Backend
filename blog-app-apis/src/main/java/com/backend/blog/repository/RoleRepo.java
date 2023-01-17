package com.backend.blog.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.blog.entities.Role;

@Transactional
public interface RoleRepo extends JpaRepository<Role, Integer> {

}
