package com.codewithsaurabh.blog_app_apis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithsaurabh.blog_app_apis.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
