package com.spring.webapp.dao.securityDAO;

import com.spring.webapp.entity.securityEntity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO extends JpaRepository<Role, Long> {
}