package com.example.susie_demo1_contacts.domain.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByPhoneNumberIn(List<String> phoneNumbers);
}
