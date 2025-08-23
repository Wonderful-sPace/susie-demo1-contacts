package com.example.susie_demo1_contacts.domain.contact.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.susie_demo1_contacts.domain.contact.dto.ContactSyncDto;
import com.example.susie_demo1_contacts.domain.contact.service.ContactService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/sync")
    public ContactSyncDto.Response sync(@RequestBody ContactSyncDto.Request request) {
        return contactService.sync(request);
    }
}
