package com.spring.database.jpa.chap04.repository;

import com.spring.database.jpa.chap04.enitity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
