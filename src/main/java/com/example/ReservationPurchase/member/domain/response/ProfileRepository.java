package com.example.ReservationPurchase.member.domain.response;

import org.springframework.web.multipart.MultipartFile;

public interface ProfileRepository {

    String upload(MultipartFile file);

    void delete(String fileName);
}
