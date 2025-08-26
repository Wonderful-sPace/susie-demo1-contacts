package com.example.susie_demo1_contacts.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.susie_demo1_contacts.domain.contact.entity.Contact;
import com.example.susie_demo1_contacts.domain.user.User;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByOwner(User owner);
}

