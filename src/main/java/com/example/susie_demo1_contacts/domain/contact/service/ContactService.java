package com.example.susie_demo1_contacts.domain.contact.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.susie_demo1_contacts.domain.contact.dto.ContactDto;
import com.example.susie_demo1_contacts.domain.contact.entity.Contact;
import com.example.susie_demo1_contacts.domain.repository.ContactRepository;
import com.example.susie_demo1_contacts.domain.user.User;
import com.example.susie_demo1_contacts.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    // 연락처 동기화 (내 주소록 업데이트)
    public void sync(User owner, List<ContactDto.ContactItem> items) {
        // 일단 기존 연락처 삭제 (단순화)
        contactRepository.deleteAll(contactRepository.findByOwner(owner));

        // 새 연락처 저장
        for (var item : items) {
            // User 테이블에서 가입자 찾기
            var linkedUser = userRepository.findByPhoneNumber(item.getPhoneNumber()).orElse(null);

            Contact contact = Contact.builder()
                    .owner(owner)
                    .phoneNumber(item.getPhoneNumber())
                    .displayName(item.getDisplayName())
                    .linkedUser(linkedUser)
                    .status(Contact.ContactStatus.NORMAL)
                    .build();

            contactRepository.save(contact);
        }
    }

    // 내 연락처 조회
    public List<ContactDto.Response> list(User owner) {
        return contactRepository.findByOwner(owner).stream()
                .map(c -> ContactDto.Response.builder()
                        .id(c.getId())
                        .phoneNumber(c.getPhoneNumber())
                        .displayName(c.getDisplayName())
                        .profileImageUrl(c.getLinkedUser() != null ? c.getLinkedUser().getProfileImageUrl() : null)
                        .registered(c.getLinkedUser() != null)
                        .status(c.getStatus().name())
                        .build()
                ).toList();
    }
}
