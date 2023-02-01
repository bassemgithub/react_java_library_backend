package com.hastega.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hastega.demo.Model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
