package com.example.susie_demo1_contacts.domain.contact.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.susie_demo1_contacts.domain.contact.dto.ContactDto;
import com.example.susie_demo1_contacts.domain.contact.service.ContactService;
import com.example.susie_demo1_contacts.domain.user.User;
import com.example.susie_demo1_contacts.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;
    private final UserRepository userRepository;

    // 🔹 동기화 (userId를 쿼리파라미터나 body로 받아옴)
    @PostMapping("/sync")
    public void sync(@RequestParam Long userId,
                     @RequestBody ContactDto.Request request) {
        User me = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음: " + userId));
        contactService.sync(me, request.getContacts());
    }

    // 🔹 내 연락처 불러오기
    @GetMapping
    public List<ContactDto.Response> list(@RequestParam Long userId) {
        User me = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음: " + userId));
        return contactService.list(me);
    }
}
