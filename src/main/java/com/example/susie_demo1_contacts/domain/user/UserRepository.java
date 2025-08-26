package com.example.susie_demo1_contacts.domain.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);
    List<User> findByPhoneNumberIn(List<String> phoneNumbers);
}
