package com.example.ReservationPurchase.auth.application.port;

import java.util.Map;

public interface RefreshRepository {

    String save(final String refreshToken, final Long memberId, final long duration);

    String findByValue(String value);

    void delete(String value);

    Map<String, String> getAllFromHash(String hashName);

    void removeFromHash(String hashName, String key);

    void addToHash(String hashName, String key, String value);


}
