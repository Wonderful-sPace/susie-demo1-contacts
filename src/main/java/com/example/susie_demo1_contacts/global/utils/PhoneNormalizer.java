package com.example.susie_demo1_contacts.global.utils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

public class PhoneNormalizer {
    private static final PhoneNumberUtil util = PhoneNumberUtil.getInstance();

    public static String toE164(String raw, String regionDefault) {
        if (raw == null || raw.isBlank()) return null;
        try {
            var num = util.parse(raw, (regionDefault == null || regionDefault.isBlank()) ? "KR" : regionDefault);
            if (!util.isValidNumber(num)) return null;
            return util.format(num, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException e) {
            return null;
        }
    }

    public static List<String> normalizeList(List<String> raw, String region) {
        if (raw == null) return List.of();
        Set<String> out = new LinkedHashSet<>();
        for (String r : raw) {
            String e164 = toE164(r, region);
            if (e164 != null) out.add(e164);
        }
        return List.copyOf(out);
    }
}
