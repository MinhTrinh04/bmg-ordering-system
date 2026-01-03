package com.banhmygac.ordering_system.util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class SlugUtil {
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-&/]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String makeSlug(String input) {
        if (input == null) throw new IllegalArgumentException();

        String customInput = input.replace("đ", "d").replace("Đ", "d");
        String nowhitespace = WHITESPACE.matcher(customInput).replaceAll("-");

        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        slug = slug.replaceAll("-+", "-");
        slug = slug.replaceAll("^-|-$", "");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
//Note: Frontend bắt buộc phải mã hóa URL slug trước khi gọi API bởi djt me ae giữ dấu / và & trong code
//VD:
//const slug = "sua-chua/thach"; Slug lấy từ database
//        const safeSlug = encodeURIComponent(slug); Kết quả: "sua-chua%2Fthach"
//
//// Gọi API với slug đã mã hóa
//fetch(`http://localhost:8080/api/v1/products/${safeSlug}`)
//        .then(response => ...)