package com.app.dflow.repository;

import com.app.dflow.object.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
