package com.example.susie_demo1_contacts.domain.contact.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.susie_demo1_contacts.domain.contact.dto.ContactSyncDto;
import com.example.susie_demo1_contacts.domain.user.User;
import com.example.susie_demo1_contacts.domain.user.User.AccountStatus;
import com.example.susie_demo1_contacts.domain.user.UserRepository;
import com.example.susie_demo1_contacts.global.utils.PhoneNormalizer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {
    private static final int MAX_CONTACTS = 3000;
    private static final int BATCH = 800;

    private final UserRepository userRepository;

    public ContactSyncDto.Response sync(ContactSyncDto.Request request) {
        List<String> incoming = Optional.ofNullable(request.getContacts()).orElse(List.of());
        if (incoming.size() > MAX_CONTACTS) {
            throw new IllegalArgumentException("contacts too large (max " + MAX_CONTACTS + ")");
        }

        var normalized = PhoneNormalizer.normalizeList(incoming, request.getRegion());

        List<User> found = new ArrayList<>();
        for (int i = 0; i < normalized.size(); i += BATCH) {
            var slice = normalized.subList(i, Math.min(i + BATCH, normalized.size()));
            found.addAll(userRepository.findByPhoneNumberIn(slice));
        }

        // ACTIVE만
        found = found.stream().filter(u -> u.getStatus() == AccountStatus.ACTIVE).toList();

        var registeredPhones = found.stream().map(User::getPhoneNumber).collect(Collectors.toSet());
        var unregistered = normalized.stream().filter(p -> !registeredPhones.contains(p)).toList();

        var registered = found.stream().map(u ->
                ContactSyncDto.Registered.builder()
                        .id(u.getId())
                        .phoneNumber(u.getPhoneNumber())
                        .displayName(u.getDisplayName())
                        .profileImageUrl(u.getProfileImageUrl())
                        .build()
        ).toList();

        return ContactSyncDto.Response.builder()
                .registered(registered)
                .unregistered(unregistered)
                .build();
    }
}
