package com.example.susie_demo1_contacts.domain.contact.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class ContactDto {

    @Getter @Setter
    public static class Request {
        private List<ContactItem> contacts;
    }

    @Getter @Setter
    public static class ContactItem {
        private String phoneNumber;
        private String displayName;
    }

    @Builder @Getter
    public static class Response {
        private Long id;
        private String phoneNumber;
        private String displayName;
        private String profileImageUrl; // linkedUser 있으면 User 정보
        private boolean registered;
        private String status; // NORMAL, BLOCKED
    }
}
