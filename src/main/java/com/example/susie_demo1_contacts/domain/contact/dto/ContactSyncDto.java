package com.example.susie_demo1_contacts.domain.contact.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class ContactSyncDto {
    @Getter @Setter
    public static class Request {
        private List<String> contacts; // 클라에서 보낸 전화번호들
        private String region;         // "KR" 기본
    }

    @Builder @Getter
    public static class Registered {
        private Long id;
        private String phoneNumber;
        private String displayName;
        private String profileImageUrl;
    }

    @Builder @Getter
    public static class Response {
        private List<Registered> registered;
        private List<String> unregistered;
    }
}
